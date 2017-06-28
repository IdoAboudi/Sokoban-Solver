package solver.search.sokspecific;

import java.util.HashMap;

import model.data.level.Box;
import model.data.level.LevelObject;
import model.data.level.Player;
import model.data.level.Point;
import model.data.level.Wall;
import searchable.Searchable;
import sharedSearch.SearchAction;
import sharedSearch.Solution;
import sharedSearch.State;

/**
 * Supplies all required information for a search algorithm to 
 * search for a box path.
 * @author Or Priesender
 *
 */
public class BoxPathSearchable implements Searchable<Point> {

	private Point dst;
	private Point src;
	private LevelObject[][] map;
	private Point currentPos;
	private Point playerSrc;
	private Point newPlayerPos;
	private LevelObject savedObjectForBox;
	private LevelObject savedObjectForPlayer;

	/**
	 * Initialize data members
	 * @param src source position
	 * @param dst destination position
	 * @param map current level map
	 */
	public BoxPathSearchable(Point src, Point dst,LevelObject[][] map) {
		this.src = src;
		this.dst = dst;
		this.map = map;
		playerSrc = getPlayerSource();
		newPlayerPos = playerSrc;
	}

	/**
	 * Supplies the inital state of the level.
	 */
	@Override
	public State<Point> getInitialState() {
		return new State<Point>(src);
	}

	/**
	 * Supplies the goal state which is the box's destination.
	 */
	@Override
	public State<Point> getGoalState() {
		return new State<Point>(dst);
	}

	/**
	 * Supplies all possible moves for a box, including a place for the player to push it.
	 */
	@Override
	public HashMap<SearchAction, State<Point>> getAllPossibleMoves(State<Point> current) {
		this.currentPos = current.getState();
		HashMap<SearchAction,State<Point>> possibles = new HashMap<>();
		PlayerPathSearchable playerSearchUp;
		PlayerPathSearchable playerSearchDown;
		PlayerPathSearchable playerSearchRight;
		PlayerPathSearchable playerSearchLeft;
		MyBFS bfs = new MyBFS();
		Point curr = current.getState();
		int j = curr.getX();
		int i = curr.getY();
		Solution solution;

		//not initial state
		if(current.getCameFrom() != null){
			updateMap(current);
		}
		//first check is for not exceeding map limits, second for movement
		if(i-1 >= 0 && i+1 >= 0 && i-1<map.length && i+1 < map.length  && j<map[i-1].length && j >=0 && j<map[i+1].length){	
			//both above and below the box is free, can push both ways with room for player
			if(!(map[i-1][j] instanceof Wall) && !(map[i+1][j] instanceof Wall) ){
				if(!(map[i-1][j] instanceof Box) && !(map[i+1][j] instanceof Box)){

					playerSearchUp = new PlayerPathSearchable(newPlayerPos,getPlayerDest(current,"Up") , map);
					if((solution = bfs.search(playerSearchUp)) != null){
						SearchAction a = new SearchAction("PushUp");
						a.setMiniActions(solution.getActions());
						possibles.put(a,new State<>(new Point(i-1,j)));
					}

					playerSearchDown = new PlayerPathSearchable(newPlayerPos,getPlayerDest(current, "Down"),map);
					if((solution = bfs.search(playerSearchDown)) != null){
						SearchAction a = new SearchAction("PushDown");
						a.setMiniActions(solution.getActions());
						possibles.put(a,new State<>(new Point(i+1,j)));
					}

				}
			}
		}

		if( i < map.length && i>=0 && j-1 >=0 && j-1<map[i].length && j+1 < map[i].length && j+1 >=0){
			//both to the left and to the right is free, can push both ways with room for player
			if(!(map[i][j-1] instanceof Wall) && !(map[i][j+1] instanceof Wall)){
				if(!(map[i][j-1] instanceof Box) && !(map[i][j+1] instanceof Box)){

					playerSearchLeft = new PlayerPathSearchable(newPlayerPos,getPlayerDest(current, "Left"),map);
					if((solution = bfs.search(playerSearchLeft)) != null){
						SearchAction a = new SearchAction("PushLeft");
						a.setMiniActions(solution.getActions());
						possibles.put(a,new State<>(new Point(i,j-1)));
					}
					playerSearchRight = new PlayerPathSearchable(newPlayerPos,getPlayerDest(current, "Right"),map);
					if((solution = bfs.search(playerSearchRight)) != null){
						SearchAction a = new SearchAction("PushRight");
						a.setMiniActions(solution.getActions());
						possibles.put(a,new State<>(new Point(i,j+1)));
					}
				}
			}	

		}
		if(current.getCameFrom() != null)
			returnMapToNormal();
		return possibles;
	}

	/**
	 * Brings the map back to what it was.
	 */
	private void returnMapToNormal() {
		map[newPlayerPos.getY()][newPlayerPos.getX()] = savedObjectForPlayer;
		map[currentPos.getY()][currentPos.getX()] = savedObjectForBox;

		map[src.getY()][src.getX()] = new Box(src);
		map[playerSrc.getY()][playerSrc.getX()] = new Player(playerSrc);
	}

	/**
	 * Set the map to fit the current state of the search algorithm.
	 * @param current
	 */
	private void updateMap(State<Point> current) {

		this.currentPos = current.getState();
		this.newPlayerPos = current.getCameFrom().getState();
		map[playerSrc.getY()][playerSrc.getX()] = null;
		map[src.getY()][src.getX()] = null;

		savedObjectForBox = map[currentPos.getY()][currentPos.getX()];
		map[currentPos.getY()][currentPos.getX()] = new Box(currentPos);

		savedObjectForPlayer = map[newPlayerPos.getY()][newPlayerPos.getX()];
		map[newPlayerPos.getY()][newPlayerPos.getX()] = new Player(newPlayerPos);
	}


	/**
	 * Calculate the player's position according to the box position
	 * @param current current state the algorithm is in
	 * @param direction direction of movement
	 * @return
	 */
	private Point getPlayerDest(State<Point> current, String direction) {
		int x = current.getState().getX();
		int y = current.getState().getY();
		switch(direction){
		case "Up":return new Point(y+1,x);
		case "Down":return new Point(y-1,x);
		case "Right":return new Point(y,x-1);
		case "Left":return new Point(y,x+1);
		default:break;
		}
		return null;
	}

	private Point getPlayerSource() {
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[i].length;j++){
				if(map[i][j] instanceof Player){
					return new Point(i,j);
				}
			}
		}
		return null;
	}
}




