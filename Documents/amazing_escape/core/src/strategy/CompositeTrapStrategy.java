package strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import controller.AIController;
import tiles.GrassTrap;
import tiles.LavaTrap;
import tiles.MapTile;
import tiles.MudTrap;
import utilities.Coordinate;
import world.WorldSpatial;

public class CompositeTrapStrategy implements TrapStrategy {
	
	List<TrapStrategy> trapStrategyList = new ArrayList<TrapStrategy>();

	public void addStrategy(TrapStrategy trapStrategy) {
		trapStrategyList.add(trapStrategy);
	}

	@Override
	public boolean checkTrapAhead(AIController ai) {
	
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());
		
		HashMap<String,Integer> traps = new HashMap<String,Integer>();
		traps.clear();
		
		int lavaTrap = 0;
		int mudTrap = 0;
		int grassTrap = 0;

		switch(orientation){
		case NORTH:
			for(int i = 0; i <= 2; i++){
				for(int j = 0; j <= 2; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x + i, currentPosition.y + j));
					if(tile.getName().equals("Trap")) {
						if (tile instanceof LavaTrap) {
							lavaTrap += 1;
						}
						else if (tile instanceof MudTrap) {
							mudTrap += 1;
						}
						else if (tile instanceof GrassTrap) {
							grassTrap += 1;
						}
					}
				}
			}
			break;
		case EAST:
			for(int i = 0; i <= 2; i++){
				for(int j = 0; j <= 2; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x + j, currentPosition.y - i));
					if(tile.getName().equals("Trap")) {
						if (tile instanceof LavaTrap) {
							lavaTrap += 1;
						}
						else if (tile instanceof MudTrap) {
							mudTrap += 1;
						}
						else if (tile instanceof GrassTrap) {
							grassTrap += 1;
						}
					}
				}
			}
			break;
		case SOUTH:
			for(int i = 0; i <= 2; i++){
				for(int j = 0; j <= 2; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x - i, currentPosition.y - j));
					if(tile.getName().equals("Trap")) {
						if (tile instanceof LavaTrap) {
							lavaTrap += 1;
						}
						else if (tile instanceof MudTrap) {
							mudTrap += 1;
						}
						else if (tile instanceof GrassTrap) {
							grassTrap += 1;
						}
					}
				}
			}
			break;
		case WEST:
			for(int i = 0; i <= 2; i++){
				for(int j = 0; j <= 2; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x - j, currentPosition.y + i));
					if(tile.getName().equals("Trap")) {
						if (tile instanceof LavaTrap) {
							lavaTrap += 1;
						}
						else if (tile instanceof MudTrap) {
							mudTrap += 1;
						}
						else if (tile instanceof GrassTrap) {
							grassTrap += 1;
						}
					}
				}
			}
			break;
			
		default:
			System.out.println("undefined orientation");
			return false;
		}
		
		traps.put("lavaTrap",lavaTrap);
		traps.put("mudTrap",mudTrap);
		traps.put("grassTrap",grassTrap);
		
		Iterator<String> itr = traps.keySet().iterator();
		String maxTrapType = itr.next();
		Integer max = traps.get(maxTrapType);
		while(itr.hasNext()) {
		    String k = itr.next();
		    Integer val = traps.get(k);
		    if (val > max){
		         max = val;
		         maxTrapType = k;
		    }
		}
		
		if (maxTrapType.equals("lavaTrap")) {
			System.out.println("lava");
			return trapStrategyList.get(0).checkTrapAhead(ai);
		}
		else if (maxTrapType.equals("mudTrap")) {
			System.out.println("mud");
			return trapStrategyList.get(1).checkTrapAhead(ai);
		}
		else if (maxTrapType.equals("grassTrap")){
			System.out.println("grass");
			return trapStrategyList.get(2).checkTrapAhead(ai);
		}
		else{
			System.out.println("composite doesn't know what strategy to use");
			return false;
		}
	}

	@Override
	public boolean checkFollowingTrap(AIController ai) {
		return trapStrategyList.get(0).checkFollowingTrap(ai);
	}

}
