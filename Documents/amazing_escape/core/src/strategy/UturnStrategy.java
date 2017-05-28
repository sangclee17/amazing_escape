package strategy;

import java.util.HashMap;

import controller.AIController;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class UturnStrategy implements DeadEndStrategy{

	/*
	 * 
	 * 
	 * */
	@Override
	public void getOutOfDeadEnd(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();

		Coordinate deadEnd = new Coordinate(ai.getPosition());

		switch(orientation){
		case NORTH:

			ai.removeAllDeadEndTiles();

			for(int i = 0; i <= 4; i++){
				MapTile tile = currentView.get(new Coordinate(deadEnd.x - 2 + i, deadEnd.y + 3));
				ai.addDeadEndTiles(tile);
			}

			ai.setRightTurning(true);

			break;
		case EAST:
			ai.removeAllDeadEndTiles();

			for(int i = 0; i <= 4; i++){
				MapTile tile = currentView.get(new Coordinate(deadEnd.x + 3, deadEnd.y - 2 + i));
				ai.addDeadEndTiles(tile);
			}

			ai.setRightTurning(true);
			break;
		case SOUTH:

			ai.removeAllDeadEndTiles();

			for(int i = 0; i <= 4; i++){
				MapTile tile = currentView.get(new Coordinate(deadEnd.x - 2 + i, deadEnd.y - 3));
				ai.addDeadEndTiles(tile);
			}
			ai.setRightTurning(true);
			break;

		case WEST:
			ai.removeAllDeadEndTiles();
			for(int i = 0; i <= 4; i++){
				MapTile tile = currentView.get(new Coordinate(deadEnd.x - 3, deadEnd.y - 2 + i));
				ai.addDeadEndTiles(tile);
			}
			ai.setRightTurning(true);
			break;
		}

	}
	
	@Override
	public boolean outOfDeadEnd(AIController ai) {
		return true;
	}
}
