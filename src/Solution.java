import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Solution {
	private List<RobotStep> solutionSteps;

	public Solution() {
		solutionSteps = new LinkedList<RobotStep>();
	}

	public Solution addStep(String robotDirection, GridPosition robotPosition, String action) {
		solutionSteps.add(new RobotStep(action, robotDirection, robotPosition));
		return this;
	}

	public Solution copy() {
		List<RobotStep> newSteps = new ArrayList<RobotStep>();
		for (RobotStep step : solutionSteps) {
			newSteps.add(step);
		}
		Solution newSolution = new Solution();
		newSolution.setSolutionSteps(newSteps);
		return newSolution;
	}

	private Solution setSolutionSteps(List<RobotStep> newSteps) {
		this.solutionSteps = newSteps;
		return null;
	}

	public List<RobotStep> getSolutionSteps() {
		return solutionSteps;
	}

	public void printSolution() {
		for (RobotStep step : solutionSteps) {
			System.out.println(step.toString());
		}
	}
}
