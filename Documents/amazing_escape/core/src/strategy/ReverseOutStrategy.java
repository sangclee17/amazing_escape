package strategy;

import java.util.ArrayList;
import java.util.HashMap;

import controller.AIController;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class ReverseOutStrategy implements DeadEndStrategy{
	
	ArrayList<MapTile> deadEndTiles = new ArrayList<MapTile>();

	@Override
	public boolean detectDeadEnd(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();

		switch(orientation){
		case NORTH:
			if(ai.checkEast(currentView) && ai.checkWest(currentView)) {
				ai.trapSensitivity = 3;
				if (ai.checkNorth(currentView) || ai.checkNorthForTrap(currentView)) {
					System.out.println("dead end!!!!");
					System.out.println("enter" + ai.getPosition());
					ai.trapSensitivity = 2;
					return true;
				}
			}
			return false;
		case EAST:
			if(ai.checkNorth(currentView) && ai.checkSouth(currentView)) {
				if (ai.checkSouth(currentView) || ai.checkSouthForTrap(currentView)) {
					System.out.println("dead end!!!!");
					return true;
				}
			}
			return false;
		case SOUTH:
			if(ai.checkEast(currentView) && ai.checkWest(currentView)) {
				if (ai.checkSouth(currentView) || ai.checkSouthForTrap(currentView)) {
					System.out.println("dead end!!!!");
					return true;
				}
			}
			return false;
		case WEST:
			if(ai.checkNorth(currentView) && ai.checkSouth(currentView)) {
				if (ai.checkWest(currentView) || ai.checkWestForTrap(currentView)) {
					System.out.println("dead end!!!!");
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}

	@Override
	public boolean outOfDeadEnd(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		
		switch(orientation){
		case NORTH:
			if(!ai.checkEast(currentView) && !ai.checkWest(currentView)) {
				deadEndTiles.clear();
				
				Coordinate deadEnd = new Coordinate(ai.getPosition());
				
				for(int i = 0; i <= 4; i++){
					MapTile tile = currentView.get(new Coordinate(deadEnd.x - 2 + i, deadEnd.y));
					deadEndTiles.add(tile);
				}
				
					return true;
			}
			return false;
		case EAST:
			return false;
		case SOUTH:
			return false;
		case WEST:
			return false;
		default:
			return false;
		}
	}
	
	public boolean isDeadEnd(AIController ai) {
		
		if(deadEndTiles.isEmpty()) {
			return false;
		}
		
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());
		
		switch(orientation){
		case EAST:
			//check North
			for(int i = 0; i <= 2; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y + i));
				if(deadEndTiles.contains(tile)){
					return true;
				}
			}
			return false;
		case WEST:
			//check South
			for(int i = 0; i <= 2; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y - i));
				if(deadEndTiles.contains(tile)){
					return true;
				}
			}
			return false;
		case NORTH:
		//check West
			for(int i = 0; i <= 2; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x - i, currentPosition.y));
				if(deadEndTiles.contains(tile)){
					return true;
				}
			}
			return false;
		case SOUTH:
			//check East
			for(int i = 0; i <= 2; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x + i, currentPosition.y));
				if(deadEndTiles.contains(tile)){
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}

}
