package strategy;

import java.util.HashMap;

import controller.AIController;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class ReverseOutStrategy implements DeadEndStrategy{
	
	@Override
	public void getOutOfDeadEnd(AIController ai) {
		ai.setReverse(true);
		ai.applyReverseAcceleration();
	}

	@Override
	public boolean outOfDeadEnd(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		
		switch(orientation){
		case NORTH:
			if(!ai.checkEast(currentView) && !ai.checkWest(currentView)) {
				ai.removeAllDeadEndTiles();
				Coordinate deadEnd = new Coordinate(ai.getPosition());
				
				for(int i = 0; i <= 4; i++){
					MapTile tile = currentView.get(new Coordinate(deadEnd.x - 2 + i, deadEnd.y));
					ai.addDeadEndTiles(tile);
				}
				
					return true;
			}
			return false;
		case EAST:
			if(!ai.checkEast(currentView) && !ai.checkWest(currentView)) {
				ai.removeAllDeadEndTiles();
				Coordinate deadEnd = new Coordinate(ai.getPosition());
				
				for(int i = 0; i <= 4; i++){
					MapTile tile = currentView.get(new Coordinate(deadEnd.x, deadEnd.y- 2 + i));
					ai.addDeadEndTiles(tile);
				}
				
					return true;
			}
			return false;
		case SOUTH:
			if(!ai.checkEast(currentView) && !ai.checkWest(currentView)) {
				ai.removeAllDeadEndTiles();
				Coordinate deadEnd = new Coordinate(ai.getPosition());
				
				for(int i = 0; i <= 4; i++){
					MapTile tile = currentView.get(new Coordinate(deadEnd.x - 2 + i, deadEnd.y));
					ai.addDeadEndTiles(tile);
				}
				
					return true;
			}
			return false;
		case WEST:
			if(!ai.checkEast(currentView) && !ai.checkWest(currentView)) {
				ai.removeAllDeadEndTiles();
				Coordinate deadEnd = new Coordinate(ai.getPosition());
				
				for(int i = 0; i <= 4; i++){
					MapTile tile = currentView.get(new Coordinate(deadEnd.x, deadEnd.y - 2 + i));
					ai.addDeadEndTiles(tile);
				}
				
					return true;
			}
			return false;
		default:
			return false;
		}
	}
}
