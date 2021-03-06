package solver.strips.sokspecific;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import model.data.level.LevelObject;
import model.data.level.Point;
import searcher.Searcher;
import sharedSearch.SearchAction;
import sharedSearch.Solution;
import solver.search.sokspecific.BoxPathSearchable;
import solver.search.sokspecific.MyBFS;
import solver.search.sokspecific.PlayerPathSearchable;

/**
 * Implementation of the class SokPredicate.
 * This predicate returns true if there is a path for 
 * a player pushing a box from a source to a destination.
 * @author Or Priesender
 *
 */
public class BoxAndPlayerPath extends SokPredicate {

	BoxPathSearchable boxSearch;
	PlayerPathSearchable playerSearch;
	Searcher<Point> searcher;
	Point src,dst;
	LevelObject[][] map;
	SearchAction lastPush;
	List<SearchAction> allActions;
	boolean satisfied = false;

	/**
	 * Initialize parameters
	 * @param id which box is it
	 * @param src initial box position
	 * @param dst wanted box position
	 * @param map current level map
	 */
	public BoxAndPlayerPath(String id,String src,String dst,LevelObject[][] map) {
		super("BoxAndPlayerPath", id, dst);
		boxSearch = new BoxPathSearchable(stringToPoint(src), stringToPoint(dst), map);
		searcher = new MyBFS();
		this.src = stringToPoint(src);
		this.dst = stringToPoint(dst);
		this.map = map;
		allActions = new ArrayList<>();
		selfSatisfied = true;
	}

	/**
	 * Translates a string to Point.
	 * @param str string to be translated
	 * @return
	 */
	private Point stringToPoint(String str){
		String[] s = str.split(",");
		return new Point(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
	}


	/**
	 * Check if this predicate is satisfied.
	 */
	@Override
	public boolean isSatisfied(){
		if(!satisfied){
			Solution s = searcher.search(boxSearch);
			if(s == null)
				return false;
			else{
				allActions.addAll(s.getActions());
				if(!allActions.isEmpty())
					lastPush = allActions.get(0);
			}

		}
		//if we made it this far it means the predicate is either already satisfied and will not
		//search for a solution again, or it searched for a solution and it was successful,
		//either way we update mini actions and flag it as satisfied.
		this.setMiniActions(allActions);
		satisfied = true;
		return true;

	}

	/**
	 * Gets the last box push needed to complete this predicate.
	 * @return
	 */
	public SearchAction getLastPush(){
		if(isSatisfied())
			return lastPush;
		else return null;
	}



}
