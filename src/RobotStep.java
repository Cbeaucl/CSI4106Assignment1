
public class RobotStep {
	private String status, robotDirection;
	private GridPosition robotPosition;

	public RobotStep(String status, String robotDirection, GridPosition robotPosition) {
		this.status = status;
		this.robotDirection = robotDirection;
		this.robotPosition = robotPosition;
	}

	@Override
	public String toString() {
		return "RobotStep [status=" + status + ", robotDirection=" + robotDirection + ", robotPosition=" + robotPosition
				+ "]";
	}

}
