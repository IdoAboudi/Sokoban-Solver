package solver.search.sokspecific;

import java.util.HashMap;

import model.data.level.Box;
import model.data.level.LevelObject;
import model.data.level.Point;
import model.data.level.Wall;
import searchable.Searchable;
import sharedSearch.SearchAction;
import sharedSearch.State;

/**
 * Supplies the needed information for a search algorithm to find a path for a player.
 * @author Or Priesender
 *
 */
public class PlayerPathSearchable implements Searchable<Point>{

	private Point src;
	private Point dst;
	private LevelObject[][] map;

	/**
	 * Initialize data members
	 * @param src source position
	 * @param dst destination position
	 * @param map current level map
	 */
	public PlayerPathSearchable(Point src,Point dst,LevelObject[][] map) {
		this.src = src;
		this.dst = dst;
		this.map = map;
	}

	/**
	 * Supplies the player's initial state.
	 */
	@Override
	public State<Point> getInitialState() {
		return new State<Point>(src);
	}

	/**
	 * Supplies the player's goal state.
	 */
	@Override
	public State<Point> getGoalState() {

		return new State<Point>(dst);
	}

	/**
	 * Supplies all possible moves the search algorithm can make at the current state.
	 */
	@Override
	public HashMap<SearchAction, State<Point>> getAllPossibleMoves(State<Point> current) {
		HashMap<SearchAction,State<Point>> possibles = new HashMap<>();
		Point curr = current.getState();
		int j = curr.getX();
		int i = curr.getY();

		if(i-1<map.length && i-1 >= 0 && j>=0 && j<map[i-1].length ){			
			if(!(map[i-1][j] instanceof Wall) && !(map[i-1][j] instanceof Box)){//move up
				possibles.put(new SearchAction("MoveUp"), new State<>(new Point(i-1,j)));
			}
		}
		if(i+1 < map.length && i+1 >= 0 && j >=0 && j<map[i+1].length){
			if(!(map[i+1][j] instanceof Wall) && !(map[i+1][j] instanceof Box)){//move down
				possibles.put(new SearchAction("MoveDown"), new State<>(new Point(i+1,j)));
			}			
		}
		if( i < map.length && i>=0 && j-1 >=0 && j-1<map[i].length){
			if(!(map[i][j-1] instanceof Wall) && !(map[i][j-1] instanceof Box)){//move left
				possibles.put(new SearchAction("MoveLeft"), new State<>(new Point(i,j-1)));
			}			
		}
		if(  i<map.length && i>=0 && j+1 < map[i].length && j+1 >=0){
			if(!(map[i][j+1] instanceof Wall) && !(map[i][j+1] instanceof Box)){//move right
				possibles.put(new SearchAction("MoveRight"), new State<>(new Point(i,j+1)));
			}			
		}
		return possibles;
	}

}
