package solver.strips.sokspecific;

import model.data.level.Point;
import searchable.Searchable;
import searcher.Searcher;
import sharedSearch.SearchAction;
import sharedSearch.Solution;

/**
 * This predicate returns true if there is a path for a player alone from source to destination.
 * @author Or Priesender
 *
 */
public class PlayerPath extends SokPredicate {
	
	private Searcher<Point> searcher = null;
	private Searchable<Point> searchable = null;

	/**
	 * Initialize data members.
	 * @param id player id
	 * @param value source position
	 * @param searcher a searcher to search the level
	 * @param searchable a searchable level
	 */
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
