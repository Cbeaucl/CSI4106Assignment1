import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Grid {
	GridNode[][] map;
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
			for (int j = 0; j < gridSize; j++) {
				GridPosition position = new GridPosition(j, i);
				map[i][j] = new GridNode(false, false, false, position);
			}
		}

		for (GridPosition position : dirtPostions) {
			map[position.getY()][position.getX()].setHasDirt(true);
		}
		for (GridPosition position : obsticalPostion) {
			map[position.getY()][position.getX()].setHasObstacle(true);
		}
		GridPosition roboPosition = robot.getCurrentPostion();
		map[roboPosition.getY()][roboPosition.getX()].setHasRobot(true);
	}

	public void moveRobotTo(GridPosition position) {
		GridPosition oldPosition = robot.getCurrentPostion();
		robot.setCurrentPostion(position);
		map[oldPosition.getY()][oldPosition.getX()].setHasRobot(false);
		map[position.getY()][position.getX()].setHasRobot(true);
	}

	public boolean isClean() {
		boolean clean = true;
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (map[i][j].isHasDirt()) {
					clean = false;
					break;
				}
			}
		}
		return clean;
	}

	public GridNode getNodeFromPostion(GridPosition postion) {
		return map[postion.getY()][postion.getX()];
	}

	public Grid copy() {
		List<GridPosition> newdirtList = new ArrayList<GridPosition>(dirtPostions.size());
		for (GridPosition position : dirtPostions) {
			newdirtList.add(position);
		}
		Robot newRobot = new Robot(robot.getCurrentPostion(), this.robot.getCurrentDirection(), robot.getSpentEnergy());
		return new Grid(newdirtList, obsticalPostion, newRobot, gridSize);
	}

	public boolean moveForward() {
		GridPosition position = robot.getCurrentPostion();
		boolean moved = false;
		switch (robot.getCurrentDirection()) {
		case "w":

			if (position.getX() - 1 >= 0) {
				if (!map[position.getY()][position.getX() - 1].isHasObstacle()) {
					map[position.getY()][position.getX() - 1].setHasRobot(true);
					map[position.getY()][position.getX()].setHasRobot(false);
					robot.setCurrentPostion(new GridPosition(position.getX() - 1, position.getY()));
					moved = true;
				}
			}
			break;
		case "e":
			if (position.getX() + 1 < gridSize) {
				if (!map[position.getY()][position.getX() + 1].isHasObstacle()) {
					map[position.getY()][position.getX() + 1].setHasRobot(true);
					map[position.getY()][position.getX()].setHasRobot(false);
					robot.setCurrentPostion(new GridPosition(position.getX() + 1, position.getY()));
					moved = true;
				}
			}
			break;
		case "n":
			if (position.getY() - 1 >= 0) {
				if (!map[position.getY() - 1][position.getX()].isHasObstacle()) {
					map[position.getY() - 1][position.getX()].setHasRobot(true);
					map[position.getY()][position.getX()].setHasRobot(false);
					robot.setCurrentPostion(new GridPosition(position.getX(), position.getY() - 1));
					moved = true;
				}
			}
			break;
		case "s":
			if (position.getY() + 1 < gridSize) {
				if (!map[position.getY() + 1][position.getX()].isHasObstacle()) {
					map[position.getY() + 1][position.getX()].setHasRobot(true);
					map[position.getY()][position.getX()].setHasRobot(false);
					robot.setCurrentPostion(new GridPosition(position.getX(), position.getY() + 1));
					moved = true;
				}
			}
			break;

		default:
			break;
		}
		if (moved) {
			robot.useEnergy(50);
		}
		return moved;
	}

	public GridNode checkForward() {
		GridPosition position = robot.getCurrentPostion();
		boolean moved = false;
		switch (robot.getCurrentDirection()) {
		case "w":

			if (position.getX() - 1 >= 0) {
				if (!map[position.getY()][position.getX() - 1].isHasObstacle()) {
					return map[position.getY()][position.getX() - 1];
				}
			}
			break;
		case "e":
			if (position.getX() + 1 < gridSize) {
				if (!map[position.getY()][position.getX() + 1].isHasObstacle()) {
					return map[position.getY()][position.getX() + 1];
				}
			}
			break;
		case "n":
			if (position.getY() - 1 >= 0) {
				if (!map[position.getY() - 1][position.getX()].isHasObstacle()) {
					return map[position.getY() - 1][position.getX()];
				}
			}
			break;
		case "s":
			if (position.getY() + 1 < gridSize) {
				if (!map[position.getY() + 1][position.getX()].isHasObstacle()) {
					return map[position.getY() + 1][position.getX()];
				}
			}
			break;

		default:
			break;
		}
		return null;
	}

	public Grid turnRight() {
		switch (robot.getCurrentDirection()) {
		case "w":
			robot.setCurrentDirection("n");
			break;
		case "e":
			robot.setCurrentDirection("s");
			break;
		case "n":
			robot.setCurrentDirection("e");
			break;
		case "s":
			robot.setCurrentDirection("w");
			break;

		default:
			break;
		}
		robot.useEnergy(20);
		return this;
	}

	public Grid turnLeft() {
		switch (robot.getCurrentDirection()) {
		case "w":
			robot.setCurrentDirection("s");
			break;
		case "e":
			robot.setCurrentDirection("n");
			break;
		case "n":
			robot.setCurrentDirection("w");
			break;
		case "s":
			robot.setCurrentDirection("e");
			break;

		default:
			break;
		}
		robot.useEnergy(20);
		return this;
	}

	public List<GridNode> getNeighbors(GridPosition position) {
		List<GridNode> neighbors = new LinkedList<GridNode>();
		if (position.getX() - 1 >= 0) {

			if (!map[position.getY()][position.getX() - 1].isHasObstacle()) {
				neighbors.add(map[position.getY()][position.getX() - 1]);
			}
		}
		if (position.getX() + 1 < gridSize) {
			if (!map[position.getY()][position.getX() + 1].isHasObstacle()) {

				neighbors.add(map[position.getY()][position.getX() + 1]);
			}
		}
		if (position.getY() - 1 >= 0) {
			if (!map[position.getY() - 1][position.getX()].isHasObstacle()) {
				neighbors.add(map[position.getY() - 1][position.getX()]);
			}
		}
		if (position.getY() + 1 < gridSize) {
			if (!map[position.getY() + 1][position.getX()].isHasObstacle()) {
				neighbors.add(map[position.getY() + 1][position.getX()]);
			}
		}
		return neighbors;
	}

	public void suck() {
		GridPosition currentPosition = robot.getCurrentPostion();
		dirtPostions.remove(currentPosition);
		if (map[currentPosition.getY()][currentPosition.getX()].isHasDirt()) {
			map[currentPosition.getY()][currentPosition.getX()].setHasDirt(false);
		}
		robot.useEnergy(10);
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
		System.out.println("\n");
	}
}
