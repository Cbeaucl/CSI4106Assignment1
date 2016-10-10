import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Chris B
 *
 */
/**
 * @author Chris B
 *
 */
/**
 * @author Chris B
 *
 */
/**
 * @author Chris B
 *
 */
/**
 * @author Chris B
 *
 */
/**
 * @author Chris B
 *
 */
public class Grid {
	GridNode[][] map;
	int gridSize;
	List<GridPosition> dirtPostions;
	List<GridPosition> obsticalPostion;
	Solution solution;
	List<GridNode> visited;
	Robot robot;

	/**
	 * Constructor for grid new grid. The grid object also serves as a means of
	 * maintaing state. As such, it also maintains a list of visited nodes, as
	 * well as a soltuion.
	 * 
	 * @param dirtPostions
	 *            The X Y coordinates of the dirts in the grid.
	 * @param obstaclePostion
	 *            The X Y coordinates of the obstacles in the grid.
	 * @param robot
	 *            The {@link #robot} associated with the grid.
	 * @param gridSize
	 *            The size of the grid. Grid will be square n by n.
	 * @param solution
	 *            The solution that goes along with the grid.
	 * @param visited
	 *            The #GridNode objects that indicate the nodes that have been
	 *            visited so far in this state.
	 */
	public Grid(List<GridPosition> dirtPostions, List<GridPosition> obstaclePostion, Robot robot, int gridSize,
			Solution solution, List<GridNode> visited) {
		this.gridSize = gridSize;
		this.map = new GridNode[gridSize][gridSize];
		this.dirtPostions = dirtPostions;
		this.obsticalPostion = obstaclePostion;
		this.robot = robot;
		this.solution = solution;
		this.visited = visited;
		// Constructing the n by n grid using a loop.
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				GridPosition position = new GridPosition(j, i);
				map[i][j] = new GridNode(false, false, false, position);
			}
		}
		// Placing the obstacles and the dirts in the grids.
		for (GridPosition position : dirtPostions) {
			map[position.getY()][position.getX()].setHasDirt(true);
		}
		for (GridPosition position : obstaclePostion) {
			map[position.getY()][position.getX()].setHasObstacle(true);
		}
		// Setting the robot position in the grid, useful for printing the grid.
		GridPosition roboPosition = robot.getCurrentPostion();
		map[roboPosition.getY()][roboPosition.getX()].setHasRobot(true);
	}

	// Helper mehtod that verifies if the grid is clean.
	public boolean isClean() {
		if (dirtPostions.size() == 0) {
			return true;
		}
		return false;
		/*
		 * boolean clean = true; for (int i = 0; i < gridSize; i++) { for (int j
		 * = 0; j < gridSize; j++) { if (map[i][j].isHasDirt()) { clean = false;
		 * break; } } } return clean;
		 */
	}

	/**
	 * Returns the #GridNode from the grid.
	 * 
	 * @param postion
	 *            The coordinates of the node.
	 * @return
	 */
	public GridNode getNodeFromPostion(GridPosition postion) {
		return map[postion.getY()][postion.getX()];
	}

	/**
	 * Takes in a #GridNode and determines the number of cells between the node
	 * and the closest dirt on the grid.
	 * 
	 * @param node
	 *            The node to get the distance from.
	 * @return
	 */
	public int getDistanceFromDirt(GridNode node) {
		int minDistance = Integer.MAX_VALUE;
		GridPosition nodePosition = node.getPosition();
		for (GridPosition postion : dirtPostions) {
			int xDistance = nodePosition.getX() - postion.getX();
			int yDisstance = nodePosition.getY() - postion.getY();
			int distance = Math.abs(xDistance) + Math.abs(yDisstance);
			if (minDistance > distance) {
				minDistance = distance;
			}
		}
		return minDistance;
	}

	/**
	 * Method that creates a hard copy of the grid and it's objects. This is
	 * necessary to be able to keep track of the states as we move through
	 * algos.
	 * 
	 * @return
	 */
	public Grid copy() {
		List<GridNode> newVisited = new ArrayList<GridNode>();
		for (GridNode newCopy : visited) {
			newVisited.add(newCopy);
		}
		List<GridPosition> newdirtList = new ArrayList<GridPosition>(dirtPostions.size());
		for (GridPosition position : dirtPostions) {
			newdirtList.add(position);
		}
		Robot newRobot = new Robot(new GridPosition(robot.getCurrentPostion().getX(), robot.getCurrentPostion().getY()),
				this.robot.getCurrentDirection(), robot.getSpentEnergy());
		return new Grid(newdirtList, obsticalPostion, newRobot, gridSize, solution.copy(), newVisited);
	}

	/**
	 * Move the robot forward if possible, otherwise return false.
	 * 
	 * @return
	 */
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

	/**
	 * Check and see if it is possible for the robot to move forward.
	 * 
	 * @return
	 */
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

	/**
	 * Returns a list of neighboring nodes for which it is possible to move to
	 * from the given node.
	 * 
	 * @param position
	 *            The position in the grid to check.
	 * @return
	 */
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

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	public List<GridNode> getVisited() {
		return visited;
	}

	public void setVisited(List<GridNode> visited) {
		this.visited = visited;
	}

	/**
	 * Commands the robot to suck the current tile it finds itself on.
	 */
	public void suck() {
		GridPosition currentPosition = robot.getCurrentPostion();
		dirtPostions.remove(currentPosition);
		if (map[currentPosition.getY()][currentPosition.getX()].isHasDirt()) {
			map[currentPosition.getY()][currentPosition.getX()].setHasDirt(false);
		}
		robot.useEnergy(10);
	}

	/**
	 * Print out the grid.
	 */
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
