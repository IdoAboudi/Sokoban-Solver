package solver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import strips.Action;

public class PlanSolution implements Serializable{

	List<String> actions = new ArrayList<>();
	
	public PlanSolution(){
		
	}
	
	public PlanSolution(List<Action> actions){
		
		for(Action a : actions){
			this.actions.add(a.getType());
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(String s : actions){
			sb.append(s + " ");
		}
		return sb.toString();
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}
	
	
}
