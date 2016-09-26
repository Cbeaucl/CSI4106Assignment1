import java.util.List;

public class Grid {
	private GridNode[][] map;
	int gridSize;
	List<GridPosition> dirtPostions;
	List<GridPosition> obsticalPostion;
	Robot robot;

	public Grid(List<GridPosition> dirtPostions, List<GridPosition> obsticalPostion, Robot robot, int gridSize) {
		this.gridSize = gridSize;
		this.map = new GridNode[gridSize][gridSize];
		this.dirtPostions = dirtPostions;
		this.obsticalPostion = obsticalPostion;
		this.robot = robot;

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++)
				map[i][j] = new GridNode(false, false, false);
		}

		for (GridPosition position : dirtPostions) {
			map[position.getX()][position.getY()].setHasDirt(true);
		}
		for (GridPosition position : obsticalPostion) {
			map[position.getX()][position.getY()].setHasObstacle(true);
		}
		GridPosition roboPosition = robot.getCurrentPostion();
		map[roboPosition.getX()][roboPosition.getY()].setHasRobot(true);
	}

	public void printGrid() {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				System.out.print("|");
				if (map[i][j].isHasRobot() && map[i][j].isHasDirt()) {
					System.out.print("*R");
				} else if (map[i][j].isHasObstacle()) {
					System.out.print("X");
				} else if (map[i][j].isHasDirt()) {
					System.out.print("*");
				} else if (map[i][j].isHasRobot()) {
					switch (robot.getCurrentDirection()) {
					case "w":
						System.out.print("←");
						break;
					case "e":
						System.out.print("→");
						break;
					case "n":
						System.out.print("↑");
						break;
					case "s":
						System.out.print("↓");
						break;

					default:
						break;
					}
				} else {
					System.out.print(" ");
				}
				System.out.print("|");
			}
			System.out.println("");
		}
	}
}
