package strategy;

import java.util.ArrayList;
import java.util.HashMap;
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
	public boolean traverse(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());
		
		switch(orientation) {
		case NORTH:
				for(int i = 0; i <= 2; i++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y + i));
					if (tile instanceof LavaTrap) {
						return trapStrategyList.get(0).traverse(ai);
					}
					else if (tile instanceof MudTrap) {
						return trapStrategyList.get(1).traverse(ai);
					}
					else if (tile instanceof GrassTrap) {
						return trapStrategyList.get(2).traverse(ai);
					}
				}
		case SOUTH:
			for(int i = 0; i <= 2; i++) {
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y - i));
				if (tile instanceof LavaTrap) {
					return trapStrategyList.get(0).traverse(ai);
				}
				else if (tile instanceof MudTrap) {
					return trapStrategyList.get(1).traverse(ai);
				}
				else if (tile instanceof GrassTrap) {
					return trapStrategyList.get(2).traverse(ai);
				}
			}
		case EAST:
			for(int i = 0; i <= 2; i++) {
				MapTile tile = currentView.get(new Coordinate(currentPosition.x + i, currentPosition.y));
				if (tile instanceof LavaTrap) {
					return trapStrategyList.get(0).traverse(ai);
				}
				else if (tile instanceof MudTrap) {
					return trapStrategyList.get(1).traverse(ai);
				}
				else if (tile instanceof GrassTrap) {
					return trapStrategyList.get(2).traverse(ai);
				}
			}
		case WEST:
			for(int i = 0; i <= 2; i++) {
				MapTile tile = currentView.get(new Coordinate(currentPosition.x - i, currentPosition.y));
				if (tile instanceof LavaTrap) {
					return trapStrategyList.get(0).traverse(ai);
				}
				else if (tile instanceof MudTrap) {
					return trapStrategyList.get(1).traverse(ai);
				}
				else if (tile instanceof GrassTrap) {
					return trapStrategyList.get(2).traverse(ai);
				}
			}
		default:
		return false;
		}
	}

	@Override
	public boolean keepFollowingTrap(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());
		
		switch(orientation) {
		case NORTH:
				for(int i = 0; i <= 2; i++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y + i));
					if (tile instanceof LavaTrap) {
						return trapStrategyList.get(0).keepFollowingTrap(ai);
					}
					else if (tile instanceof MudTrap) {
						return trapStrategyList.get(1).keepFollowingTrap(ai);
					}
					else if (tile instanceof GrassTrap) {
						return trapStrategyList.get(2).keepFollowingTrap(ai);
					}
				}
		case SOUTH:
			for(int i = 0; i <= 2; i++) {
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y - i));
				if (tile instanceof LavaTrap) {
					return trapStrategyList.get(0).keepFollowingTrap(ai);
				}
				else if (tile instanceof MudTrap) {
					return trapStrategyList.get(1).keepFollowingTrap(ai);
				}
				else if (tile instanceof GrassTrap) {
					return trapStrategyList.get(2).keepFollowingTrap(ai);
				}
			}
		case EAST:
			for(int i = 0; i <= 2; i++) {
				MapTile tile = currentView.get(new Coordinate(currentPosition.x + i, currentPosition.y));
				if (tile instanceof LavaTrap) {
					return trapStrategyList.get(0).keepFollowingTrap(ai);
				}
				else if (tile instanceof MudTrap) {
					return trapStrategyList.get(1).keepFollowingTrap(ai);
				}
				else if (tile instanceof GrassTrap) {
					return trapStrategyList.get(2).keepFollowingTrap(ai);
				}
			}
		case WEST:
			for(int i = 0; i <= 2; i++) {
				MapTile tile = currentView.get(new Coordinate(currentPosition.x - i, currentPosition.y));
				if (tile instanceof LavaTrap) {
					return trapStrategyList.get(0).keepFollowingTrap(ai);
				}
				else if (tile instanceof MudTrap) {
					return trapStrategyList.get(1).keepFollowingTrap(ai);
				}
				else if (tile instanceof GrassTrap) {
					return trapStrategyList.get(2).keepFollowingTrap(ai);
				}
			}
		default:
		return false;
		}
	}
}
