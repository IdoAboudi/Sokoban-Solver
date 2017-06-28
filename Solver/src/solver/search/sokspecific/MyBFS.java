package solver.search.sokspecific;

import model.data.level.Point;
import searchable.Searchable;
import searcher.BFS;
import sharedSearch.SearchAction;
import sharedSearch.Solution;

public class MyBFS extends BFS<Point> {

	public String getFirstActionName(Searchable<Point> searchable){
		Solution s = this.search(searchable);
		if(s!=null){
			SearchAction a = s.getActions().getLast();
			return a.getName();
		}
		else return null;
	}

}
