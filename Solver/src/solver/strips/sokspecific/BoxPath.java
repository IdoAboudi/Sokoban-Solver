package solver.strips.sokspecific;

import model.data.level.Point;
import searchable.Searchable;
import sharedSearch.Solution;
import solver.search.sokspecific.BoxPathSearchable;
import solver.search.sokspecific.MyBFS;

/**
 * Implementation of the SokPredicate class.
 * This predicate returns true if there is a path for a box from source to destination.
 * @author Or Priesender
 *
 */
public class BoxPath extends SokPredicate {

	private MyBFS searcher = null;
	private BoxPathSearchable searchable = null;

	/**
	 * Initialize data members
	 * @param id box id
	 * @param value box location
	 * @param searcher a map searcher
	 * @param searchable a map searchable fitting for a box.
	 */
	public BoxPath(String id,String value,MyBFS searcher,BoxPathSearchable searchable) {
		super("BoxPath",id,value);
		selfSatisfied=true;
		this.searchable = searchable;
		this.searcher = searcher;


	}

	/**
	 * Checks if there is a path for the box.
	 */
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
