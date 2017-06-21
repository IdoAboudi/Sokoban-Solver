package solver;

import java.util.List;

import model.data.level.Level;
import solver.strips.sokspecific.PlannableLevel;
import strips.Action;
import strips.Planner;
import strips.Strips;

public class SokobanSolver {

	public List<Action> solve(Level lvl){
		Planner planner = new Strips();
		return planner.plan(new PlannableLevel(lvl));
	}
}
