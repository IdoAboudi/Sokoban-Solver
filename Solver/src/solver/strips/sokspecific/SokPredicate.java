package solver.strips.sokspecific;
import java.util.ArrayList;
import java.util.List;

import sharedSearch.SearchAction;
import strips.Action;
import strips.Predicate;

public class SokPredicate extends Predicate {
	
	public List<Action> miniActions = new ArrayList<>();
	
	public SokPredicate(String type, String id, String value) {
		super(type, id, value);
		
	}
	
	@Override
	public boolean contradicts(Predicate p) {
		return super.contradicts(p) || (!id.equals(p.getId()) && value.equals(p.getValue())); 
				//|| (id.equals(p.getId()) && !value.equals(p.getValue()) && !value.equals("?")));
	}
	
	public List<Action> getMiniActions(){
		return miniActions;
	}
	
	public void setMiniActions(List<SearchAction> miniActions){
		if(this.miniActions.size() != miniActions.size()){
			this.miniActions.clear();
			for(SearchAction a : miniActions){
				this.miniActions.add(new strips.Action(a.getName(), "", ""));
			}
		}
	}
	
	
	

}
