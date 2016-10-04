import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RobotSearchAlgs {

	public static void doBreadthFirst(Grid grid) {
	}

	public static Solution doDepthFirst(Grid grid) {
		grid.printGrid();
		GridPosition position = grid.robot.getCurrentPostion();
		GridNode node = grid.getNodeFromPostion(position);
		grid.getVisited().add(node);
		Solution theSolution = null;

		List<GridNode> neighbors = grid.getNeighbors(position);
		if (node.isHasDirt()) {
			grid.suck();
			grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Sucked");
		}
		if (grid.isClean()) {
			grid.getSolution().printSolution();
			System.out.println("Robot spent: " + grid.robot.getSpentEnergy());
			System.out.println("Total Moves: " + grid.getSolution().getSolutionSteps().size());
			theSolution = grid.getSolution();

		} else if (!grid.getVisited().containsAll(neighbors) && theSolution == null) {
			if (!grid.getVisited().contains(grid.checkForward()) && grid.moveForward()) {
				grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(),
						"Moved Forward");
				theSolution = doDepthFirst(grid.copy());
			}
			if (theSolution == null) {
				grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Left");
				theSolution = doDepthFirst(grid.copy().turnLeft());
			}
			if (theSolution == null) {
				grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Right");
				theSolution = doDepthFirst(grid.copy().turnRight());
			}
		}
		return theSolution;

	}

	public static Solution doAStar(Grid grid) {
		grid.printGrid();
		GridPosition position = grid.robot.getCurrentPostion();
		GridNode node = grid.getNodeFromPostion(position);
		grid.getVisited().add(node);
		Solution theSolution = null;

		List<GridNode> neighbors = grid.getNeighbors(position);
		if (node.isHasDirt()) {
			grid.suck();
			grid.getSolution().addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Sucked");
		}
		if (grid.isClean()) {
			grid.getSolution().printSolution();
			System.out.println("Robot spent: " + grid.robot.getSpentEnergy());
			System.out.println("Total Moves: " + grid.getSolution().getSolutionSteps().size());
			theSolution = grid.getSolution();

		} else if (!grid.getVisited().containsAll(neighbors) && theSolution == null) {

			Map<GridNode, Integer> costMap = calculateCost(neighbors, grid.robot);

			for (Map.Entry<GridNode, Integer> entry : costMap.entrySet()) {
				GridNode key = entry.getKey();
				int additionalCost = grid.getDistanceFromDirt(key);
				entry.setValue(entry.getValue() + (additionalCost * 21));// ...
			}
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
			for (GridNode nodeTest : test) {
				if (theSolution != null) {
					break;
				}
				String move = actionToTake(grid.robot, nodeTest);
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
