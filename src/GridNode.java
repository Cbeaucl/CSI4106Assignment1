
public class GridNode implements Cloneable {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (hasDirt ? 1231 : 1237);
		result = prime * result + (hasObstacle ? 1231 : 1237);
		result = prime * result + (hasRobot ? 1231 : 1237);
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GridNode other = (GridNode) obj;
		if (hasDirt != other.hasDirt)
			return false;
		if (hasObstacle != other.hasObstacle)
			return false;
		if (hasRobot != other.hasRobot)
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
}
