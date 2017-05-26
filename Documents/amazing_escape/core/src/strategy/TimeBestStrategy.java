package strategy;

import java.util.HashMap;

import controller.AIController;
import tiles.*;
import utilities.Coordinate;
import world.WorldSpatial;

public class TimeBestStrategy implements TrapStrategy{
	double LAVA_TRAP_WEIGHT = 0.5;
	double MUD_TRAP_WEIGHT = 0.8;
	double GRASS_TRAP_WEIGHT = 0.3;
	double WALL_WEIGHT = 10;
	
	public boolean traverse(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());

		double[] total = new double[3] ;

		switch(orientation){
		case NORTH:
			
			for(int i = 0; i <= 2; i++){
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
			if (total[0] > total[1] || total[0] > total[2]) {
				//turn right
				return false;
			}
			else {
				return true;
			}
		case EAST:
			for(int i = 0; i <= 2; i++){
				total[i] = 0.0;
				for(int j = 0; j <= 1; j++) {
					MapTile tile = currentView.get(new Coordinate(currentPosition.x + j, currentPosition.y + i));
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
			if (total[0] > total[1] || total[0] > total[2]) {
				//turn right
				System.out.println("turn right~~~total[0] " + total[0] +" total[1] " + total[1] + " total[2] " + total[2]);
				return false;
			}
			else {
				return true;
			}
		case SOUTH:
			for(int i = 0; i <= 2; i++){
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
			if (total[0] > total[1] || total[0] > total[2]) {
				//turn right
				//System.out.println("total[0] " + total[0] +" total[1] " + total[1] + " total[2] " + total[2]);
				return false;
			}
			else {
				
				return true;
			}
		case WEST:
		
			for(int i = 0; i <= 2; i++){
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
			if (total[0] > total[1] || total[0] > total[2]) {
				//turn right
				return false;
			}
			else {
				return true;
			}
		default:
			return true;
		}
	}
	
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
				//System.out.println("following south "+"total[0] " + total[0] + " total[2] " + total[2]);
				return true;
			}
				//System.out.println("turn to east "+"total[0] " + total[0] + " total[2] " + total[2]);
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
					else if (tile.getName().equals("Wall")) {
						total[i] += 10;
					}
				}
			}

			if (total[0] <= total[1]) {
				//System.out.println("following south "+"total[0] " + total[0] + " total[2] " + total[2]);
				return true;
			}
				//System.out.println("turn to east "+"total[0] " + total[0] + " total[2] " + total[2]);
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
				//System.out.println("following south "+"total[0] " + total[0] + " total[1] " + total[1]);
				return true;
			}
				//System.out.println("turn to east "+"total[0] " + total[0] + " total[2] " + total[2]);
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
				//System.out.println("following south "+"total[0] " + total[0] + " total[2] " + total[2]);
				return true;
			}
				//System.out.println("turn to east "+"total[0] " + total[0] + " total[2] " + total[2]);
				return false;
		default:
			return false;
		}
	}

	/*
	public boolean checkFollowingTrap(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		Coordinate currentPosition = new Coordinate(ai.getPosition());

		double[] total = new double[3] ;
		
		switch(orientation){
		case NORTH:
			//check West
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
					else if(tile.getName().equals("Wall")) {
						total[i] += WALL_WEIGHT;
					}
	
				}
			}
			if(total[0] == 0.0 && total[1] == 0.0) {
				return false;
			}
			if (total[0] <= total[1]) {
				//System.out.println("following south "+"total[0] " + total[0] + " total[2] " + total[2]);
				return true;
			}
				//System.out.println("turn to east "+"total[0] " + total[0] + " total[2] " + total[2]);
				return false;
		case EAST:
			//check North
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
					else if(tile.getName().equals("Wall")) {
						total[i] += WALL_WEIGHT;
					}
				}
			}
			if(total[0] == 0.0 && total[1] == 0.0) {
				return false;
			}
			if (total[0] <= total[1]) {
				//System.out.println("following south "+"total[0] " + total[0] + " total[2] " + total[2]);
				return true;
			}
				//System.out.println("turn to east "+"total[0] " + total[0] + " total[2] " + total[2]);
				return false;
		case SOUTH:
			//check East
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
					else if(tile.getName().equals("Wall")) {
						total[i] += WALL_WEIGHT;
					}
				
				}
			}
			if(total[0] == 0.0 && total[1] == 0.0) {
				return false;
			}
			if (total[0] <= total[1]) {
				//System.out.println("following south "+"total[0] " + total[0] + " total[1] " + total[1]);
				return true;
			}
				//System.out.println("turn to east "+"total[0] " + total[0] + " total[2] " + total[2]);
				return false;
		case WEST:
			//check South
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
					else if(tile.getName().equals("Wall")) {
						total[i] += WALL_WEIGHT;
					}
				}
			}
			if(total[0] == 0.0 && total[1] == 0.0) {
				return false;
			}
			if (total[0] <= total[1]) {
				//System.out.println("following south "+"total[0] " + total[0] + " total[2] " + total[2]);
				return true;
			}
				//System.out.println("turn to east "+"total[0] " + total[0] + " total[2] " + total[2]);
				return false;
		default:
			return false;
		}
	}
	*/
}



