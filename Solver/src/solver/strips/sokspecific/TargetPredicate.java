package solver.strips.sokspecific;

import strips.Predicate;

public class TargetPredicate extends SokPredicate {

	public TargetPredicate( String id, String value) {
		super("TargetAt", id, value);
	}

	@Override
	public boolean satisfies(Predicate p) {
		//TODO: check
		if(p.getType().equals("ClearAt")){
			return (this.value.equals(p.getValue()));
		}
		return super.satisfies(p);
	}
}
