
public class Robot {
	private GridPosition currentPostion;
	private String currentDirection;
	public Robot(GridPosition currentPostion, String currentDirection) {
		super();
		this.currentPostion = currentPostion;
		this.currentDirection = currentDirection;
	}
	public GridPosition getCurrentPostion() {
		return currentPostion;
	}
	public void setCurrentPostion(GridPosition currentPostion) {
		this.currentPostion = currentPostion;
	}
	public String getCurrentDirection() {
		return currentDirection;
	}
	public void setCurrentDirection(String currentDirection) {
		this.currentDirection = currentDirection;
	}
	
}
