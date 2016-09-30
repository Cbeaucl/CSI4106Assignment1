import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotSearchAlgs {

	public static void doBreadthFirst(Grid grid) {

	}

	public static void doDepthFirst(Grid grid) {

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
