package solver.files;

import java.io.OutputStream;

import solver.PlanSolution;

public abstract class SolutionSaver {

	public abstract void saveSolution(OutputStream out,PlanSolution s);
}
