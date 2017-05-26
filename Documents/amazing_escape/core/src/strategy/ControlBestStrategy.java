package strategy;

import java.util.HashMap;

import controller.AIController;
import tiles.*;
import utilities.Coordinate;
import world.WorldSpatial;

public class ControlBestStrategy implements TrapStrategy{

	/*
	final private double LAVA_TRAP_WEIGHT = 0.5;
	final private double MUD_TRAP_WEIGHT = 0.8;
	final private double GRASS_TRAP_WEIGHT = 0.3;

	@Override
	public boolean checkAhead(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();

		switch(orientation) {
		case NORTH:
			if(ai.checkNorth(currentView)) {
				return true;
			}
			return this.checkNorth(currentView, ai.getPosition());
		case EAST:
			if(ai.checkEast(currentView)) {
				return true;
			}

			return this.checkEast(currentView, ai.getPosition());
		case SOUTH:
			if(ai.checkSouth(currentView)) {
				return true;
			}
			return this.checkSouth(currentView, ai.getPosition());
		case WEST:
			if(ai.checkWest(currentView)) {
				return true;
			}
			return this.checkWest(currentView, ai.getPosition());
		default:
			return false;
		}
	}

	@Override
	public boolean checkFollowing(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();

		switch(orientation) {
		case NORTH:
			if(ai.checkWest(currentView)) {
				return true;
			}
			return this.checkWest(currentView, ai.getPosition());
		case EAST:
			if(ai.checkNorth(currentView)) {
				return true;
			}
			return this.checkNorth(currentView, ai.getPosition());
		case SOUTH:
			if(ai.checkEast(currentView)) {
				return true;
			}
			return this.checkEast(currentView, ai.getPosition());
		case WEST:
			if(ai.checkSouth(currentView)) {
				return true;
			}
			return this.checkSouth(currentView, ai.getPosition());
		default:
			return false;
		}
	}

	public boolean checkNorth(HashMap<Coordinate,MapTile> currentView, String position) {
		Coordinate currentPosition = new Coordinate(position);
		double[] total = new double[2] ;

		for(int i = 0; i <= 1; i++){
			total[i] = 0.0;
			for(int j = 0; j <= 2; j++) {
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
		if (total[0] >= total[1]) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkEast(HashMap<Coordinate,MapTile> currentView, String position) {
		Coordinate currentPosition = new Coordinate(position);

		double[] total = new double[2] ;

		for(int i = 0; i <= 1; i++){
			total[i] = 0.0;
			for(int j = 0; j <= 2; j++) {
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
		if (total[0] >= total[1]) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean checkSouth(HashMap<Coordinate,MapTile> currentView, String position) {
		Coordinate currentPosition = new Coordinate(position);
		double[] total = new double[2] ;

		for(int i = 0; i <= 1; i++){
			total[i] = 0.0;
			for(int j = 0; j <= 2; j++) {
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
		if (total[0] >= total[1]) {
			return true;
		}

		return false;

	}

	public boolean checkWest(HashMap<Coordinate,MapTile> currentView, String position) {
		Coordinate currentPosition = new Coordinate(position);
		double[] total = new double[2] ;

		for(int i = 0; i <= 1; i++){
			total[i] = 0.0;
			for(int j = 0; j <= 2; j++) {
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
		if (total[0] >= total[1]) {
			return true;
		}
		else {
			return false;
		}
	}

	 */

	double LAVA_TRAP_WEIGHT = 0.5;
	double MUD_TRAP_WEIGHT = 0.3;
	double GRASS_TRAP_WEIGHT = 0.8;

	public boolean checkTrapAhead(AIController ai) { 
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());

		double[] total = new double[3] ;

		switch(orientation){
		case NORTH:
			for(int i = 0; i <= 2; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 2; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x - 1 + i, currentPosition.y + j));
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
			if (total[1] > total[2]) {
				//turn right
				return true;
			}
			else {
				return false;
			}
		case EAST:
			for(int i = 0; i <= 2; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 2; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x + j, currentPosition.y + i - 1));
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
			if (total[1] > total[2]) {
				//turn right
				return true;
			}
			else {
				return false;
			}
		case SOUTH:
			for(int i = 0; i <= 2; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 2; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x + i - 1, currentPosition.y - j));
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
			if (total[1] > total[0]) {
				System.out.println("total[0] " + total[0] + " total[2] " + total[2]);
				return true;
			}
			else {
				return false;
			}
		case WEST:
			for(int i = 0; i <= 2; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 2; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x - j, currentPosition.y + i - 1));
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
			if (total[1] > total[2]) {
				return true;
			}
			else {
				return false;
			}
		default:
			return false;
		}
	}
	public boolean checkFollowingTrap(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());

		switch(orientation){
		case NORTH:
			//check West
			for(int i = 0; i <= 1; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x-i, currentPosition.y));
				if(tile.getName().equals("Trap")){
					return true;
				}
			}
			return false;

		case EAST:
			//check North
			for(int i = 0; i <= 1; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y+i));
				if(tile.getName().equals("Trap")){
					return true;
				}
			}
			return false;
		case SOUTH:
			//check East
			for(int i = 0; i <= 1; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x+i, currentPosition.y));
				if(tile.getName().equals("Trap")){
					return true;
				}
			}
			return false;
		case WEST:
			//check South
			for(int i = 0; i <= 1; i++){
				MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y-i));
				if(tile.getName().equals("Trap")){
					return true;
				}
			}
			return false;
		default:
			return false;
		}
	}
}
