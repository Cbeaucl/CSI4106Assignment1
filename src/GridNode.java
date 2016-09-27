
public class GridNode {
	private boolean hasObstacle, hasDirt, hasRobot;

	public GridNode(boolean hasObstacle, boolean hasDirt, boolean hasRobot) {
		this.hasObstacle = hasObstacle;
		this.hasDirt = hasDirt;
		this.hasRobot = hasRobot;
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
}
