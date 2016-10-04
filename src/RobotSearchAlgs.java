import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static void doAStar(Grid grid) {

	}

	private Map<GridNode, Integer> calculateCost(List<GridNode> neighbors, Robot robot) {
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
					cost += 90;
				} else if (yDif == -1) {
					cost += 50;
				} else {
					cost += 70;
				}
				break;
			case "s":
				if (yDif == 1) {
					cost += 50;
				} else if (yDif == -1) {
					cost += 90;
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
