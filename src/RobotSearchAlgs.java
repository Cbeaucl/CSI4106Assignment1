import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RobotSearchAlgs {

	public static Solution doBreadthFirst(Grid grid) {
		grid.printGrid();
		GridPosition position = grid.robot.getCurrentPostion();
		GridNode node = grid.getNodeFromPostion(position);
		grid.getVisited().add(node);
		Solution theSolution = null;

		Queue<Grid> frontier = new LinkedList<Grid>();
		Grid tempGrid = null;
		frontier.add(grid.copy());

		while (!frontier.isEmpty()) {
			Grid currentGrid = frontier.poll();
			currentGrid.printGrid();
			List<GridNode> neighbors = currentGrid.getNeighbors(position);
			if (currentGrid.getNodeFromPostion(currentGrid.robot.getCurrentPostion()).isHasDirt()) {
				currentGrid.suck();
				currentGrid.getSolution().addStep(currentGrid.robot.getCurrentDirection(),
						currentGrid.robot.getCurrentPostion(), "Sucked");
			}
			if (currentGrid.isClean()) {
				currentGrid.getSolution().printSolution();
				System.out.println("Robot spent: " + currentGrid.robot.getSpentEnergy());
				System.out.println("Total Moves: " + currentGrid.getSolution().getSolutionSteps().size());
				theSolution = currentGrid.getSolution();
				return theSolution;

			} else if (!currentGrid.getVisited().containsAll(neighbors) && theSolution == null) {
				if (!currentGrid.getVisited().contains(currentGrid.checkForward())) {
					tempGrid = currentGrid.copy();
					if (tempGrid.moveForward()) {
						tempGrid.getSolution().addStep(tempGrid.robot.getCurrentDirection(),
								tempGrid.robot.getCurrentPostion(), "Moved Forward");
						tempGrid.getVisited().add(tempGrid.getNodeFromPostion(tempGrid.robot.getCurrentPostion()));
						frontier.add(tempGrid);
					}

				}
				if (theSolution == null) {
					tempGrid = currentGrid.copy();
					tempGrid.getSolution().addStep(tempGrid.robot.getCurrentDirection(),
							tempGrid.robot.getCurrentPostion(), "Left");
					tempGrid.turnLeft();
					frontier.add(tempGrid);
				}
				if (theSolution == null) {
					tempGrid = currentGrid.copy();
					tempGrid.getSolution().addStep(currentGrid.robot.getCurrentDirection(),
							tempGrid.robot.getCurrentPostion(), "Right");
					tempGrid.turnRight();
					frontier.add(tempGrid);
				}
			}
		}
		return theSolution;
	}

	public static Solution doDepthFirst(Grid grid) {
		grid.printGrid();
		GridPosition position = grid.robot.getCurrentPostion();
		GridNode node = grid.getNodeFromPostion(position);
		grid.getVisited().add(node);
		Solution theSolution = null;
		// Get the neighbors of the robots current position.
		List<GridNode> neighbors = grid.getNeighbors(position);
		// Check to see if current position has dirt. If so, suck it.
		if (node.isHasDirt()) {
			grid.suck();
			grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Sucked");
		}
		// Check to see if the grid is clean. If so, we're done, return
		// solution.
		if (grid.isClean()) {
			grid.getSolution().setTotalEnergy(grid.robot.getSpentEnergy());
			theSolution = grid.getSolution();
			// If not, check and see if it is still posible to move.
		} else if (!grid.getVisited().containsAll(neighbors) && theSolution == null) {
			if (!grid.getVisited().contains(grid.checkForward()) && grid.moveForward()) {
				// If we can move forward, do so, and recurse.
				grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(),
						"Moved Forward");
				theSolution = doDepthFirst(grid.copy());
			}
			// If a solution isn't found, turn Left and recurse.
			if (theSolution == null) {
				grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Left");
				theSolution = doDepthFirst(grid.copy().turnLeft());
			}
			// If a solution isn't found, turn right and recurse.
			if (theSolution == null) {
				grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Right");
				theSolution = doDepthFirst(grid.copy().turnRight());
			}
		}
		return theSolution;

	}

	/**
	 * Uses a heuristic to help make decision as to where the robot should go,
	 * based on how the next move will increase it's distance from the goal. We
	 * decided to add a cost of 21 per cell between the neighboring cell and the
	 * closest piece of dirt.
	 * 
	 * @param grid
	 * @return
	 */
	public static Solution doAStar(Grid grid) {
		grid.printGrid();
		GridPosition position = grid.robot.getCurrentPostion();
		GridNode node = grid.getNodeFromPostion(position);
		grid.getVisited().add(node);
		Solution theSolution = null;

		List<GridNode> neighbors = grid.getNeighbors(position);
		// If the current node has dirt, suck the dirt.
		if (node.isHasDirt()) {
			grid.suck();
			grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Sucked");
		}
		// If it's clean, were done, return the solution.
		if (grid.isClean()) {
			grid.getSolution().setTotalEnergy(grid.robot.getSpentEnergy());
			theSolution = grid.getSolution();

		} else if (!grid.getVisited().containsAll(neighbors) && theSolution == null) {
			// Calculate the cost to reach each possible non visited node.
			Map<GridNode, Integer> costMap = calculateCost(neighbors, grid.robot);
			// Using the heuristic, add an extra cost from the goal for each of
			// the neighbors.
			for (Map.Entry<GridNode, Integer> entry : costMap.entrySet()) {
				GridNode key = entry.getKey();
				int additionalCost = grid.getDistanceFromDirt(key);
				entry.setValue(entry.getValue() + (additionalCost * 21));// ...
			}
			// Sort the nodes based on the cheapest cost and queue them.
			Queue<GridNode> test = new LinkedBlockingQueue<GridNode>();
			while (!costMap.isEmpty()) {
				int minCost = Integer.MAX_VALUE;
				GridNode cheapNode = null;
				for (Map.Entry<GridNode, Integer> entry : costMap.entrySet()) {
					if (entry.getValue() < minCost) {
						minCost = entry.getValue();
						cheapNode = entry.getKey();
					}
				}
				costMap.remove(cheapNode);
				test.add(cheapNode);
			}
			// Recurse through the list.
			for (GridNode nodeTest : test) {
				if (theSolution != null) {
					break;
				}
				// Determine which action to take based to get to the cheapest
				// node based on the cost calculations.
				String move = actionToTake(grid.robot, nodeTest);
				// Take action, recurse.
				switch (move) {
				case "f":
					grid.moveForward();
					grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(),
							"Moved Forward");
					theSolution = doAStar(grid.copy());
					break;
				case "l":
					grid.turnLeft();
					grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(),
							"Left");

					theSolution = doAStar(grid.copy());
					break;
				case "r":
					grid.turnRight();
					grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(),
							"Right");

					theSolution = doAStar(grid.copy());
				default:
					break;
				}
			}
		}
		return theSolution;

	}

	/**
	 * Determine which action to take to move the desired node. Returns null if
	 * it is impossible to move to that node.
	 * 
	 * @param robot
	 *            The robot object from the grid.
	 * @param node
	 *            The node we want to move towards.
	 * @return
	 */
	private static String actionToTake(Robot robot, GridNode node) {
		int xDif = robot.getCurrentPostion().getX() - node.getPosition().getX();
		int yDif = robot.getCurrentPostion().getY() - node.getPosition().getY();
		switch (robot.getCurrentDirection()) {
		case "w":
			if (xDif == 1) {
				return "f";
			} else if (xDif == -1) {
				return "l";
			} else if (yDif == -1) {
				return "l";
			} else if (yDif == 1) {
				return "r";
			}
			break;
		case "e":
			if (xDif == 1) {
				return "r";
			} else if (xDif == -1) {
				return "f";
			} else if (yDif == -1) {
				return "r";
			} else if (yDif == 1) {
				return "l";
			}
			break;
		case "n":
			if (yDif == 1) {
				return "f";
			} else if (yDif == -1) {
				return "r";
			} else if (xDif == 1) {
				return "l";
			} else if (xDif == -1) {
				return "r";
			}
			break;
		case "s":
			if (yDif == -1) {
				return "f";
			} else if (yDif == 1) {
				return "r";
			} else if (xDif == 1) {
				return "r";
			} else if (xDif == -1) {
				return "l";
			}
			break;
		default:
			break;
		}
		return null;
	}

	private static Map<GridNode, Integer> calculateCost(List<GridNode> neighbors, Robot robot) {
		Map<GridNode, Integer> test = new HashMap<GridNode, Integer>();
		String direction = robot.getCurrentDirection();
		GridPosition robotPostion = robot.getCurrentPostion();
		for (GridNode neighbor : neighbors) {
			int cost = 0;
			int xDif = robotPostion.getX() - neighbor.getPosition().getX();
			int yDif = robotPostion.getY() - neighbor.getPosition().getY();
			switch (direction) {
			case "w":
				if (xDif == 1) {
					cost += 50;
				} else if (xDif == -1) {
					cost += 90;
				} else {
					cost += 70;
				}
				break;
			case "e":
				if (xDif == 1) {
					cost += 90;
				} else if (xDif == -1) {
					cost += 50;
				} else {
					cost += 70;
				}
				break;
			case "n":
				if (yDif == 1) {
					cost += 50;
				} else if (yDif == -1) {
					cost += 90;
				} else {
					cost += 70;
				}
				break;
			case "s":
				if (yDif == 1) {
					cost += 90;
				} else if (yDif == -1) {
					cost += 50;
				} else {
					cost += 70;
				}
				break;
			default:
				break;
			}
			test.put(neighbor, cost);
		}
		return test;
	}
}
