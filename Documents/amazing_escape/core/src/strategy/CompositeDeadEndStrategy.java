package strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import controller.AIController;
import tiles.MapTile;
import utilities.Coordinate;
import world.WorldSpatial;

public class CompositeDeadEndStrategy implements DeadEndStrategy {
	
	List<DeadEndStrategy> deadEndStrategyList = new ArrayList<DeadEndStrategy>();

	public void addStrategy(DeadEndStrategy deadEndStrategy) {
		deadEndStrategyList.add(deadEndStrategy);
	}
	/*
	 * check if there is enough space for the car to make a right turn
	 * If it does, apply u-turn dead end strategy to get out of the dead end.
	 * If it doesn't, apply reverse back out dead end strategy.
	 * */
	@Override
	public void getOutOfDeadEnd(AIController ai) {
		HashMap<Coordinate, MapTile> currentView = ai.getView();
		WorldSpatial.Direction orientation = ai.getOrientation();
		
		switch(orientation) {
		
		case NORTH:
			if(!ai.checkEast(currentView) && !ai.checkEastForTrap(currentView)){
				//check if there is enough room on the right side of the car, make a u turn
				deadEndStrategyList.get(0).getOutOfDeadEnd(ai);
			}
			else {
				//if not, reverse back out.
				deadEndStrategyList.get(1).getOutOfDeadEnd(ai);
			}
			break;
		case SOUTH:
			if(!ai.checkWest(currentView)) {
				deadEndStrategyList.get(0).getOutOfDeadEnd(ai);
			}
			else {
				deadEndStrategyList.get(1).getOutOfDeadEnd(ai);
			}
			break;
		case EAST:
			if(!ai.checkSouth(currentView)) {
				deadEndStrategyList.get(0).getOutOfDeadEnd(ai);
			}
			else {
				deadEndStrategyList.get(1).getOutOfDeadEnd(ai);
			}
			break;
		case WEST:
			if(!ai.checkNorth(currentView)) {
				deadEndStrategyList.get(0).getOutOfDeadEnd(ai);
			}
			else {
				deadEndStrategyList.get(1).getOutOfDeadEnd(ai);
			}
			break;
		}
	}

	public boolean outOfDeadEnd(AIController ai) {
		return deadEndStrategyList.get(1).outOfDeadEnd(ai);
	}
}
