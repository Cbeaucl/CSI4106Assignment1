
public class GridNode {
	private boolean hasObstacle, hasDirt, hasRobot;
	private GridPosition position;

	public GridNode(boolean hasObstacle, boolean hasDirt, boolean hasRobot, GridPosition position) {
		this.hasObstacle = hasObstacle;
		this.hasDirt = hasDirt;
		this.hasRobot = hasRobot;
		this.position = position;
	}

	public boolean isHasObstacle() {
		return hasObstacle;
	}

	public void setHasObstacle(boolean hasObstical) {
		this.hasObstacle = hasObstical;
	}

	public boolean isHasDirt() {
		return hasDirt;
	}

	public void setHasDirt(boolean hasDirt) {
		this.hasDirt = hasDirt;
	}

	public boolean isHasRobot() {
		return hasRobot;
	}

	public void setHasRobot(boolean hasRobot) {
		this.hasRobot = hasRobot;
	}

	public GridPosition getPosition() {
		return position;
	}

	public void setPosition(GridPosition position) {
		this.position = position;
	}
}
