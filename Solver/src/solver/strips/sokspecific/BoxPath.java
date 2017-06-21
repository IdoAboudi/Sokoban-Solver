package solver.strips.sokspecific;

import java.util.List;

import model.data.level.Point;
import searchable.Searchable;
import sharedSearch.Solution;
import solver.search.sokspecific.BoxPathSearchable;
import solver.search.sokspecific.MyBFS;
import strips.Action;


public class BoxPath extends SokPredicate {
	
	private MyBFS searcher = null;
	private BoxPathSearchable searchable = null;

	public BoxPath(String id,String value,MyBFS searcher,BoxPathSearchable searchable) {
		super("BoxPath",id,value);
		selfSatisfied=true;
		this.searchable = searchable;
		this.searcher = searcher;
		
		
	}
	
	@Override
	public boolean isSatisfied() {
		Solution s = searcher.search(searchable);
		if(s!=null){
			setMiniActions(s.getActions());
			return true;
		}
		else return false;
	}

	public MyBFS getSearcher() {
		return searcher;
	}

	public void setSearcher(MyBFS searcher) {
		this.searcher = searcher;
	}

	public Searchable<Point> getSearchable() {
		return searchable;
	}

	public void setSearchable(BoxPathSearchable searchable) {
		this.searchable = searchable;
	}
}
