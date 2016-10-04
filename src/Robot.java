
public class Robot {
	private GridPosition currentPostion;
	private String currentDirection;
	private int spentEnergy;

	public Robot(GridPosition currentPostion, String currentDirection, int spentEnergy) {
		this.currentPostion = currentPostion;
		this.currentDirection = currentDirection;
		this.spentEnergy = spentEnergy;
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

	public void useEnergy(int total) {
		spentEnergy += total;
	}

	public int getSpentEnergy() {
		return this.spentEnergy;
	}

}
