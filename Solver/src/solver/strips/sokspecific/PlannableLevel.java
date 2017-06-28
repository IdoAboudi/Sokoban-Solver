package solver.strips.sokspecific;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.data.level.Box;
import model.data.level.Destination;
import model.data.level.Level;
import model.data.level.LevelObject;
import model.data.level.Player;
import model.data.level.Point;
import model.data.level.Wall;
import strips.Action;
import strips.Clause;
import strips.Plannable;
import strips.Predicate;

/**
 * Object adapter for the Level class. supplies the required information a plan problem needs.
 * @author Or Priesender
 *
 */
public class PlannableLevel implements Plannable {
	
	Level lvl;
	Clause kb;
	Point playerPos;
	int boxes,targets;
	Map<String,Predicate> usedBoxes = new HashMap<>();
	LevelObject[][] map;


	
	public PlannableLevel(Level lvl) {
		this.lvl = lvl;
	}


	/**
	 * Returns a clause containing the predicates that describe the current level situation.
	 */
	@Override
	public Clause getKnowledgeBase() {
		if(kb!=null)
			return kb;
		else kb = new Clause(null);
		this.map = lvl.getMap();
			this.boxes=0;
			this.targets=0;
			for(int i=0;i<map.length;i++){
				for(int j=0;j<map[i].length;j++){
					if(map[i][j] instanceof Box){
						boxes++;
						kb.addPredicate(new SokPredicate("BoxAt","b"+boxes,""+i+","+j)); 
					}
					else if(map[i][j] instanceof Destination){
						targets++;
						kb.addPredicate(new TargetPredicate("t"+targets,""+i+","+j)); 
					}
					else if(map[i][j] instanceof Player){
						kb.addPredicate(new SokPredicate("PlayerAt","p",""+i+","+j)); 
						playerPos = map[i][j].getPosition();
					}
					else if(map[i][j] instanceof Wall){
						kb.addPredicate(new SokPredicate("WallAt","",""+i+","+j)); 
					}
					else if(map[i][j]==null){
						kb.addPredicate(new ClearPredicate("",""+i+","+j)); 
					}
				}
			}
		
		return kb;
	}

	/**
	 * Returns a clause containing predicates needed to be fulfilled in order to complete the problem.
	 */
	@Override
	public Clause getGoal() {
		Clause goal = new Clause(null);
		if(kb == null){
			getKnowledgeBase();
		}
		for(Predicate p : kb.getPredicates()){
			if(p.getType().equals("TargetAt"))
				goal.addPredicate(new SokPredicate("BoxAt", "?", p.getValue()));
				
		}
		return goal;
	}

	/**
	 * Returns a list of actions which their effects can satisfy the given predicate.
	 */
	@Override
	public Set<Action> getSatisfyingActions(Predicate p) {
		
		Set<Action> actions = new HashSet<>();
		if(p.getType().equals("BoxAt")){
			for(Predicate pr : getKnowledgeBase().getPredicates())
				if(pr.getType().equals("BoxAt")){
					if(pr.getType().equals("BoxAt") && !usedBoxes.containsKey(pr.getId())){
						LevelObject[][] newMap  = buildMapFromKB(map);
						Action action = new MoveBox(pr.getId(),pr.getValue(),p.getValue(),newMap);
						if(action.getMiniActions() != null){
								usedBoxes.put(pr.getId(), pr);
								actions.add(action);
								break;
						}
							
					}
				}
			}
		return actions;
	}

	/**
	 * Builds a LevelObject map from the knowledge base.
	 * @param map current level map
	 * @return a level object map
	 */
	private LevelObject[][] buildMapFromKB(LevelObject[][] map) {
		LevelObject[][] newMap = new LevelObject[map.length][];
		for(int i =0 ; i< map.length ; i++){
			newMap[i] = new LevelObject[map[i].length];
		}
		Clause kb = getKnowledgeBase();
		for(Predicate p : kb.getPredicates()){
			String[] vals = p.getValue().split(",");
			int i = Integer.parseInt(vals[0]);
			int j = Integer.parseInt(vals[1]);
			switch(p.getType()){
			case "BoxAt":
				newMap[i][j]=new Box(new Point(i,j));break;
			case "TargetAt":
				newMap[i][j]=new Destination(new Point(i,j));break;
			case "WallAt":
				newMap[i][j]=new Wall(new Point(i,j));break;
			case "PlayerAt":
				newMap[i][j]=new Player(new Point(i,j));
				break;
				default:break;
			}
		}
		this.map = newMap;
		return newMap;
	}

	/**
	 * Choose the most fitting action returned from getSatisfyingActions.
	 */
	@Override
	public Action getSatisfyingAction(Predicate p) {
		Set<Action> actions = this.getSatisfyingActions(p);
		Action chosen = null;
		int min = Integer.MAX_VALUE;
		if(actions != null){
			for(Action a : actions){
				//the distance between the source of the box to its destination
				if(!usedBoxes.containsKey(p.getId())){
				int distance = distance(this.boxPositionById(p.getId()),p.getValue());
				if(min > distance){
					min = distance;
					chosen=(MoveBox)a;
				}
				}
			}
		}
		return chosen;
	}

	/**
	 * Get a box position by its ID.
	 * @param id
	 * @return
	 */
	private String boxPositionById(String id) {
		if(kb!=null){
			for(Predicate p : kb.getPredicates()){
				if(p.getType().equals("BoxAt")&&(p.getId().equals(id)||id.equals("?"))){
					return p.getValue();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Calculate the distance between two points. 
	 * being used to choose the most fitting action for a predicate.
	 * @param src source position
	 * @param dst destination position
	 * @return distance value
	 */
	private int distance(String src,String dst){
		String[] srcArr = src.split(",");
		String[] dstArr = dst.split(",");
		int x = Integer.parseInt(srcArr[0]);
		int y = Integer.parseInt(srcArr[1]);
		int a = Integer.parseInt(dstArr[0]);
		int b = Integer.parseInt(dstArr[1]);
		
		int res1 = (x-a)*(x-a);
		int res2 = (y-b)*(y-b);
		
		int euclidian = (int) Math.sqrt(res1+res2);
		return euclidian;
	}
}