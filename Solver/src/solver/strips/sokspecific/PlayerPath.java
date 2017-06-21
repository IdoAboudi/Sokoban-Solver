package solver.strips.sokspecific;

import model.data.level.Point;
import searchable.Searchable;
import searcher.Searcher;
import sharedSearch.SearchAction;
import sharedSearch.Solution;

public class PlayerPath extends SokPredicate {
	
	private Searcher<Point> searcher = null;
	private Searchable<Point> searchable = null;

	public PlayerPath(String id, String value,Searcher<Point> searcher,Searchable<Point>searchable) {
		super("PlayerPath", id, value);
		this.searchable = searchable;
		this.searcher = searcher;
		selfSatisfied = true;
	}
	
	public Searchable<Point> getSearchable() {
		return searchable;
	}

	public void setSearchable(Searchable<Point> searchable) {
		this.searchable = searchable;
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

	public Searcher<Point> getSearcher() {
		return searcher;
	}

	public void setSearcher(Searcher<Point> searcher) {
		this.searcher = searcher;
	}
	
	

}
