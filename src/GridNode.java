
public class GridNode {
	private int xPosition, Ypostion;
	private boolean hasObstical, hasDirt, hasRobot;
	public GridNode(int xPosition, int ypostion, boolean hasObstical, boolean hasDirt, boolean hasRobot) {
		this.xPosition = xPosition;
		Ypostion = ypostion;
		this.hasObstical = hasObstical;
		this.hasDirt = hasDirt;
		this.hasRobot = hasRobot;
	}
}

