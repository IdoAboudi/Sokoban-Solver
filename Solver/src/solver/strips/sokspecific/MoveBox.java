package solver.strips.sokspecific;

import java.util.ArrayList;
import java.util.List;

import model.data.level.LevelObject;
import model.data.level.Player;
import model.data.level.Point;
import sharedSearch.SearchAction;
import strips.Action;
import strips.Clause;
/**
 * This SokAction is in charge of moving a box from source to destination.
 * It defines the preconditions and effects needed for this action.
 * @author Or Priesender
 *
 */
public class MoveBox extends SokAction {

	/**
	 * Initialize data members and calculate preconditions and effects
	 * @param id the box id to move
	 * @param src location of the box
	 * @param dst wanted location of the box
	 * @param map current level map 
	 */
	public MoveBox(String id,String src, String dst,LevelObject[][] map) {
		super("MoveBox", id, dst);
		this.effects = new Clause();
		this.preconditions = new Clause();

		BoxAndPlayerPath boxAndPlayer = new BoxAndPlayerPath(id, src, dst, map);
		preconditions.addPredicate(boxAndPlayer);
		preconditions.addPredicate(new ClearPredicate("", dst));

		effects.addPredicate(new SokPredicate("BoxAt", id, dst));
		effects.addPredicate(new SokPredicate("ClearAt", "", src));

		if(!boxAndPlayer.isSatisfied())
			return;
		else {
			SearchAction last = boxAndPlayer.getLastPush();
			Point playerDest = getPlayerDest(dst,last.getName());	
			effects.addPredicate(new SokPredicate("PlayerAt", "p",playerDest.toString()));
			List<Action> actions = new ArrayList<>();
			actions.addAll(boxAndPlayer.getMiniActions());
			this.setMiniActions(actions);
		}
	}

	/**
	 * Calculates the player destination according to where the box should arrive.
	 * @param boxDst box destination
	 * @param actionName last action the box made. example: "Push Left"
	 * @return
	 */
	private Point getPlayerDest(String boxDst, String actionName) {
		String[] s = boxDst.split(",");
		int y = Integer.parseInt(s[0]);
		int x = Integer.parseInt(s[1]);
		String direction = actionName.replace("Push", "");
		switch(direction){
		case "Up": return new Point(y+1,x);
		case "Down": return new Point(y-1,x);
		case "Right":return new Point(y,x-1);
		case "Left":return new Point(y,x+1);
		default:return null;
		}
	}

}
