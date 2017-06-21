package solver.strips.sokspecific;



import java.util.ArrayList;
import java.util.List;

import model.data.level.LevelObject;
import model.data.level.Player;
import model.data.level.Point;
import sharedSearch.SearchAction;
import strips.Action;
import strips.Clause;

public class MoveBox extends SokAction {

	
	
	
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



	private Point stringToPoint(String str){
		String[] s = str.split(",");
		return new Point(Integer.parseInt(s[0]),Integer.parseInt(s[1]));
	}
	
	private Point findPlayer(LevelObject[][] map){
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[i].length;j++){
				if(map[i][j] instanceof Player)
					return map[i][j].getPosition();
			}
		}
		return null;
	}
	//finds the point where the player has to be to push the box in the right direction
	private Point getPlayerDst(String direction,String boxDst,String boxSrc, String when){
		String str = direction.replaceAll("Push", "");
		String[] dstVals = boxDst.split(",");
		String[] srcVals = boxSrc.split(",");
		int dstI = Integer.parseInt(dstVals[0]);
		int dstJ = Integer.parseInt(dstVals[1]);
		int srcI = Integer.parseInt(srcVals[0]);
		int srcJ = Integer.parseInt(srcVals[1]);
		
		if(when.compareToIgnoreCase("after") == 0){
			
			switch(str){
			case "Up": return new Point(dstI+1,dstJ);
			case "Down":return new Point(dstI-1,dstJ);
			case "Left":return new Point(dstI,dstJ+1);
			case "Right":return new Point(dstI,dstJ-1);
			
			}
		}else if(when.compareToIgnoreCase("before")==0){
			switch(str){
			case "Up": return new Point(srcI+1,srcJ);
			case "Down":return new Point(srcI-1,srcJ);
			case "Left":return new Point(srcI,srcJ+1);
			case "Right":return new Point(srcI,srcJ-1);
		}
		
	}
		return null;
	}

}
