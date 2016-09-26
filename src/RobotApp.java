import java.util.List;

public class RobotApp {

	public static void main(String[] args) {
		
	}
	
	private Grid generateGrid(List<GridPosition> dirtPostions, List<GridPosition> obsticalPostion, Robot robot)
	{
		return null;
	}
	private Solution search(Grid grid, int searchType)
	{
		switch (searchType) {
		case 1:
			RobotSearchAlgs.doDepthFirst(grid);
			break;
		case 2:
			RobotSearchAlgs.doBreadthFirst(grid);
			break;
		case 3:
			RobotSearchAlgs.doAStar(grid); 
		default:
			break;
		}
		return null;
	}
	private void printSolution(Solution solution)
	{
		
	}

}
