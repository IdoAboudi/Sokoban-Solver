package solver.strips.sokspecific;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import strips.Action;

public class SokAction extends strips.Action{

	public SokAction(String type, String id, String value) {
		super(type, id, value);
		
	}
	
	/**
	 * reverse the action list to be in the right order.
	 */
	@Override
	public List<Action> getMiniActions() {
		if(super.getMiniActions() == null)
			return super.getMiniActions();
		List<Action> mini = new ArrayList<>(super.getMiniActions());
		Collections.reverse(mini);
		return mini;
	}
	
	

	
}
