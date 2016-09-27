import java.util.LinkedList;
import java.util.List;

public class Solution {
	private List<RobotStep> solutionSteps;

	public Solution() {
		solutionSteps = new LinkedList<RobotStep>();
	}

	public void addStep(String robotDirection, GridPosition robotPosition, String action) {
		solutionSteps.add(new RobotStep(action, robotDirection, robotPosition));
	}

	public void printSolution() {
		for (RobotStep step : solutionSteps) {
			System.out.println(step.toString());
		}
	}
}
