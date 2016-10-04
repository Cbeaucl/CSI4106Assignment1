import java.util.ArrayList;
import java.util.List;

public class RobotApp {

	public static void main(String[] args) {
		GridPosition dirt1 = new GridPosition(1, 0);
		GridPosition dirt2 = new GridPosition(0, 1);
		GridPosition dirt3 = new GridPosition(2, 2);
		GridPosition dirt4 = new GridPosition(1, 3);
		List<GridPosition> dirts = new ArrayList<GridPosition>();
		dirts.add(dirt1);
		dirts.add(dirt2);
		dirts.add(dirt3);
		dirts.add(dirt4);

		List<GridPosition> obstacles = new ArrayList<GridPosition>();

		GridPosition obst1 = new GridPosition(1, 1);
		GridPosition obst2 = new GridPosition(1, 2);
		GridPosition obst3 = new GridPosition(2, 1);
		obstacles.add(obst1);
		obstacles.add(obst2);
		obstacles.add(obst3);
		Robot theRobot = new Robot(new GridPosition(3, 2), "w", 0);
		// obstacles
		Grid grid = generateGrid(dirts, obstacles, theRobot, 4);
		grid.printGrid();
		// grid.moveForward();
		// grid.printGrid();
		search(grid, 1);

	}

	private static Grid generateGrid(List<GridPosition> dirtPostions, List<GridPosition> obstaclePostion, Robot robot,
			int gridSize) {
		return new Grid(dirtPostions, obstaclePostion, robot, gridSize);

	}

	private static Solution search(Grid grid, int searchType) {
		switch (searchType) {
		case 1:
			RobotSearchAlgs.doDepthFirst(grid, new Solution(), new ArrayList<GridNode>());
			break;
		case 2:
			RobotSearchAlgs.doBreadthFirst(grid);
			break;
		case 3:
			RobotSearchAlgs.doAStar(grid);
		default:
			break;
		}
		return null;
	}

	private void printSolution(Solution solution) {

	}

}
