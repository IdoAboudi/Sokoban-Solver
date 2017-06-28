package solver.strips.sokspecific;

import strips.Predicate;

/**
 * This predicate defines a target position
 * @author Or Priesender
 *
 */
public class TargetPredicate extends SokPredicate {

	public TargetPredicate( String id, String value) {
		super("TargetAt", id, value);
	}

	@Override
	public boolean satisfies(Predicate p) {
		if(p.getType().equals("ClearAt")){
			return (this.value.equals(p.getValue()));
		}
		return super.satisfies(p);
	}
}
