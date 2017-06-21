package solver.strips.sokspecific;

import strips.Action;
import strips.Clause;

public class MovePlayer extends Action {

	public MovePlayer(String id, String value) {
		super("MovePlayer", id, value);
		
		this.preconditions = new Clause(null);
		//add playerat source predicate
		
		this.effects = new Clause(null);
		//add clear at source
		//add playerat dest
	}

}
