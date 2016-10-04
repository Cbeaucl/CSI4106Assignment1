import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotSearchAlgs {

	public static void doBreadthFirst(Grid grid) {
	}

	public static Solution doDepthFirst(Grid grid, Solution solution, List<GridNode> visited) {
		grid.printGrid();
		GridPosition position = grid.robot.getCurrentPostion();
		GridNode node = grid.getNodeFromPostion(position);
		visited.add(node);
		Solution theSolution = null;
		List<GridNode> newVisited = new ArrayList<GridNode>();
		for (GridNode newCopy : visited) {
			newVisited.add(newCopy);
		}

		List<GridNode> neighbors = grid.getNeighbors(position);
		if (node.isHasDirt()) {
			grid.suck();
			solution.addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Sucked");
		}
		if (grid.isClean()) {
			solution.printSolution();
			theSolution = solution;

		} else if (!visited.containsAll(neighbors) && theSolution == null) {
			// ArrayList<GridNode> newVisited = new
			// ArrayList<GridNode>(visited.size());
			// Collections.copy(newVisited, visited);
			if (!visited.contains(grid.checkForward()) && grid.moveForward()) {

				theSolution = doDepthFirst(grid.copy(), solution.addStep(grid.robot.getCurrentDirection(),
						grid.robot.getCurrentPostion(), "Moved Forward"), newVisited);
			}
			if (theSolution == null) {
				theSolution = doDepthFirst(grid.copy().turnLeft(),
						solution.addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Left"),
						newVisited);
			}
			if (theSolution == null) {
				theSolution = doDepthFirst(grid.copy().turnRight(),
						solution.addStep(grid.robot.getCurrentDirection(), grid.robot.getCurrentPostion(), "Right"),
						newVisited);
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
