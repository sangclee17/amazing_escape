package strategy;

import java.util.HashMap;

import controller.AIController;
import tiles.*;
import utilities.Coordinate;
import world.WorldSpatial;

public class ControlBestStrategy implements TrapStrategy{
	
	double LAVA_TRAP_WEIGHT = 0.5;
	double MUD_TRAP_WEIGHT = 0.3;
	double GRASS_TRAP_WEIGHT = 0.8;

	@Override
	public boolean traverse(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());

		int[] trapPath = new int[4] ;
		int numOfTrapsOnPath = 0;

		switch(orientation) {
		case NORTH:
			OUTER:
				for(int i = 0; i <= 2; i ++) {
					trapPath[i] = 0;
					numOfTrapsOnPath = 0;
					for(int j = 0; j <= 2; j ++) {
						MapTile tile = currentView.get(new Coordinate(currentPosition.x + i, currentPosition.y + j));
						int k = j;
						if(tile.getName().equals("Trap")) {
							while(tile.getName().equals("Trap")) {
								numOfTrapsOnPath++;
								k += 1;
								tile = currentView.get(new Coordinate(currentPosition.x + i, currentPosition.y + k)); 
								if(tile == null) {
									break;
								}
							}
							trapPath[i] = numOfTrapsOnPath;
							continue OUTER;
						}
					}
					trapPath[i] = numOfTrapsOnPath;
				}
		//go around making right turn
		if(trapPath[1] < trapPath[0] || trapPath[2] < trapPath[0]) {
			return false;
		}
		
		//go through traps
		return true;

		case SOUTH:
			OUTER:
				for(int i = 0; i <= 3; i ++) {
					trapPath[i] = 0;
					numOfTrapsOnPath = 0;
					for(int j = 0; j <= 2; j ++) {
						MapTile tile = currentView.get(new Coordinate(currentPosition.x - i + 1, currentPosition.y - j));
						int k = j;
						if(tile.getName().equals("Trap")) {
							while(tile.getName().equals("Trap")) {
								numOfTrapsOnPath++;
								k += 1;
								tile = currentView.get(new Coordinate(currentPosition.x - i + 1, currentPosition.y - k)); 
								if(tile == null) {
									break;
								}
							}
							trapPath[i] = numOfTrapsOnPath;
							continue OUTER;
						}
					}
					trapPath[i] = numOfTrapsOnPath;
				}
		//go around making right turn
		if((trapPath[1] > trapPath[2]) ||(trapPath[1] > trapPath[3])) {
			return false;
		}
		
		//go through traps
		return true;

		case EAST:
			OUTER:
				for(int i = 0; i <= 2; i ++) {
					trapPath[i] = 0;
					numOfTrapsOnPath = 0;
					for(int j = 0; j <= 2; j ++) {
						MapTile tile = currentView.get(new Coordinate(currentPosition.x + j, currentPosition.y + i - 1));
						int k = j;
						if(tile.getName().equals("Trap")) {
							while(tile.getName().equals("Trap")) {
								numOfTrapsOnPath++;
								k += 1;
								tile = currentView.get(new Coordinate(currentPosition.x + k, currentPosition.y + i - 1)); 
								if(tile == null) {
									break;
								}
							}
							trapPath[i] = numOfTrapsOnPath;
							continue OUTER;
						}
					}
					trapPath[i] = numOfTrapsOnPath;
				}
		//go around making right turn
		if(trapPath[1] > trapPath[0] && !ai.checkSouth(currentView)) {
			return false;
		}
		
		//go through traps
		return true;

		case WEST:
			OUTER:
				for(int i = 0; i <= 2; i ++) {
					trapPath[i] = 0;
					numOfTrapsOnPath = 0;
					for(int j = 0; j <= 2; j ++) {
						MapTile tile = currentView.get(new Coordinate(currentPosition.x - j, currentPosition.y + i));
						int k = j;
						if(tile.getName().equals("Trap")) {
							while(tile.getName().equals("Trap")) {
								numOfTrapsOnPath++;
								k += 1;
								tile = currentView.get(new Coordinate(currentPosition.x - k, currentPosition.y + i)); 
								if(tile == null) {
									break;
								}
							}
							trapPath[i] = numOfTrapsOnPath;
							continue OUTER;
						}
					}
					trapPath[i] = numOfTrapsOnPath;
				}
		
		//go around making right turn
		if(trapPath[1] < trapPath[0] || trapPath[2] < trapPath[0]) {
			return false;
		}
		//go through traps
		return true;

		default:
			return true;
		}
	}
	@Override
	public boolean keepFollowingTrap(AIController ai) {

		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());

		double[] total = new double[3] ;

		switch(orientation){
		case NORTH:
			//check West
			for(int i = 0; i <= 1; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 1; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x - j, currentPosition.y + i));
					if (tile instanceof LavaTrap) {
						total[i] += LAVA_TRAP_WEIGHT; 
					}
					else if (tile instanceof MudTrap) {
						total[i] += MUD_TRAP_WEIGHT;
					}
					else if (tile instanceof GrassTrap) {
						total[i] += GRASS_TRAP_WEIGHT;
					}
				}
			}
			if (total[0] <= total[1]) {
				return true;
			}
			return false;
		case EAST:
			//check North
			for(int i = 0; i <= 1; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 1; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x + i, currentPosition.y + j));
					if (tile instanceof LavaTrap) {
						total[i] += LAVA_TRAP_WEIGHT; 
					}
					else if (tile instanceof MudTrap) {
						total[i] += MUD_TRAP_WEIGHT;
					}
					else if (tile instanceof GrassTrap) {
						total[i] += GRASS_TRAP_WEIGHT;
					}
				}
			}

			if (total[0] <= total[1]) {
				return true;
			}
			return false;
		case SOUTH:
			//check East
			for(int i = 0; i <= 1; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 1; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x + j, currentPosition.y - i));
					if (tile instanceof LavaTrap) {
						total[i] += LAVA_TRAP_WEIGHT; 
					}
					else if (tile instanceof MudTrap) {
						total[i] += MUD_TRAP_WEIGHT;
					}
					else if (tile instanceof GrassTrap) {
						total[i] += GRASS_TRAP_WEIGHT;
					}
				}
			}
			if (total[0] <= total[1]) {
				return true;
			}
			return false;
		case WEST:
			//check South
			for(int i = 0; i <= 1; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 1; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x - i, currentPosition.y - j));
					if (tile instanceof LavaTrap) {
						total[i] += LAVA_TRAP_WEIGHT; 
					}
					else if (tile instanceof MudTrap) {
						total[i] += MUD_TRAP_WEIGHT;
					}
					else if (tile instanceof GrassTrap) {
						total[i] += GRASS_TRAP_WEIGHT;
					}
				}
			}
			if (total[0] <= total[1]) {
				return true;
			}
			return false;
		default:
			return false;
		}
	}
}



