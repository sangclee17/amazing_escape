package controller;

import java.util.HashMap;

import strategy.*;
import tiles.GrassTrap;
import tiles.LavaTrap;
import tiles.MapTile;
import tiles.MudTrap;
import utilities.Coordinate;
import world.Car;
import world.WorldSpatial;

public class AIController extends CarController {

	// How many minimum units the wall is away from the player.
	private int wallSensitivity = 2;
	public int trapSensitivity = 1;


	private boolean isFollowingWall = false; // This is initialized when the car sticks to a wall.
	private WorldSpatial.RelativeDirection lastTurnDirection = null; // Shows the last turn direction the car takes.
	private boolean isTurningLeft = false;
	private boolean isTurningRight = false; 
	private WorldSpatial.Direction previousState = null; // Keeps track of the previous state
	private boolean isReverse = false;

	// Car Speed to move at
	private final float CAR_SPEED = 3;

	// Offset used to differentiate between 0 and 360 degrees
	private int EAST_THRESHOLD = 3;

	private TrapStrategy strategy;
	private DeadEndStrategy deadStrategy;

	public AIController(Car car) {
		super(car);
		//strategy = StrategyFactory.getSharedInstance().getCompositeTrapStrategy();
		strategy = StrategyFactory.getSharedInstance().getTimeBestStrategy();
		deadStrategy = StrategyFactory.getSharedInstance().getReverseOutStrategy();
	}

	@Override
	public void update(float delta) {

		// Gets what the car can see
		HashMap<Coordinate, MapTile> currentView = getView();


		checkStateChange();


		// If you are not following a wall initially, find a wall to stick to!
		if(!isFollowingWall){
			if(getVelocity() < CAR_SPEED){
				applyForwardAcceleration();
			}
			// Turn towards the north
			if(!getOrientation().equals(WorldSpatial.Direction.NORTH)){
				lastTurnDirection = WorldSpatial.RelativeDirection.LEFT;
				applyLeftTurn(getOrientation(),delta);
			}
			if(checkNorth(currentView)){
				// Turn right until we go back to east!
				if(!getOrientation().equals(WorldSpatial.Direction.EAST)){
					lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
					applyRightTurn(getOrientation(),delta);
				}
				else{
					isFollowingWall = true;
				}
			}
		}
		// Once the car is already stuck to a wall, apply the following logic
		else{
			// Readjust the car if it is misaligned.
			readjust(lastTurnDirection,delta);

			if(isTurningRight){
					applyRightTurn(getOrientation(),delta);
				
			}
			else if(isTurningLeft){
				if(!checkFollowingWall(getOrientation(),currentView) && !deadStrategy.isDeadEnd(this)
						&&!checkFollowingTrap(getOrientation(),currentView)) { 
					//System.out.println("left turn applied");
					applyLeftTurn(getOrientation(),delta);
				}

				else{
					isTurningLeft = false;
				}
			}
			
			else if(isReverse) {
				applyReverseAcceleration();
				if(deadStrategy.outOfDeadEnd(this)) {
					lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
					isTurningRight = true;	
					isReverse = false;
				}
			}
			
			else if(checkFollowingTrap(getOrientation(),currentView)) {
				System.out.println("following traps");
				if(strategy.keepFollowingTrap(this)) {
					
					if (getVelocity() < CAR_SPEED){
						applyForwardAcceleration();
					}
					
					if(deadStrategy.detectDeadEnd(this)) {
						System.out.println("detect dead end");
						applyBrake();
						isReverse = true;
						return;
					}

					if(checkTrapAhead(getOrientation(),currentView)) {
						if(!strategy.traverse(this)) {
							System.out.println("there is trap ahead");
							lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
							isTurningRight = true;
						}
						return;
					}
					
					// If there is wall ahead, turn right!
					if(checkWallAhead(getOrientation(),currentView)){
						System.out.println("there is wall ahead");
						lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
						isTurningRight = true;	
						return;
					}
					return;
				}
				if(!checkFollowingWall(getOrientation(),currentView) && !deadStrategy.isDeadEnd(this)) { 
					System.out.println("left turn applied");
					applyLeftTurn(getOrientation(),delta);
				}
			}
			
			else if (checkFollowingWall(getOrientation(),currentView)) {
				System.out.println("check Following wall");
				if (getVelocity() < CAR_SPEED){
					applyForwardAcceleration();
				}
				
				if(deadStrategy.detectDeadEnd(this)) {
					System.out.println("detect dead end");
					applyBrake();
					isReverse = true;
					return;
				}

				if(checkTrapAhead(getOrientation(),currentView)) {
					System.out.println("there is trap ahead");
					if(!strategy.traverse(this)) {
						System.out.println("make a right turn");
						lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
						isTurningRight = true;
					}
					return;
				}
				
				// If there is wall ahead, turn right!
				if(checkWallAhead(getOrientation(),currentView)){
					System.out.println("there is wall ahead");
					lastTurnDirection = WorldSpatial.RelativeDirection.RIGHT;
					isTurningRight = true;	
					return;
				}
			}

			// This indicates that I can do a left turn if I am not turning right
			else{
				lastTurnDirection = WorldSpatial.RelativeDirection.LEFT;
				isTurningLeft = true;
				
				if (getVelocity() < CAR_SPEED){
					applyForwardAcceleration();
				}
				System.out.println("doesn't follow anything");
			}

		}	
	}
	/**
	 * Readjust the car to the orientation we are in.
	 * @param lastTurnDirection
	 * @param delta
	 */
	private void readjust(WorldSpatial.RelativeDirection lastTurnDirection, float delta) {
		if(lastTurnDirection != null){
			if(!isTurningRight && lastTurnDirection.equals(WorldSpatial.RelativeDirection.RIGHT)){
				adjustRight(getOrientation(),delta);
			}
			else if(!isTurningLeft && lastTurnDirection.equals(WorldSpatial.RelativeDirection.LEFT)){
				adjustLeft(getOrientation(),delta);
			}
		}
	}

	/**
	 * Try to orient myself to a degree that I was supposed to be at if I am
	 * misaligned.
	 */
	private void adjustLeft(WorldSpatial.Direction orientation, float delta) {

		switch(orientation){
		case EAST:
			if(getAngle() > WorldSpatial.EAST_DEGREE_MIN+EAST_THRESHOLD){
				turnRight(delta);
			}
			break;
		case NORTH:
			if(getAngle() > WorldSpatial.NORTH_DEGREE){
				turnRight(delta);
			}
			break;
		case SOUTH:
			if(getAngle() > WorldSpatial.SOUTH_DEGREE){
				turnRight(delta);
			}
			break;
		case WEST:
			if(getAngle() > WorldSpatial.WEST_DEGREE){
				turnRight(delta);
			}
			break;

		default:
			break;
		}

	}

	private void adjustRight(WorldSpatial.Direction orientation, float delta) {
		switch(orientation){
		case EAST:
			if(getAngle() > WorldSpatial.SOUTH_DEGREE && getAngle() < WorldSpatial.EAST_DEGREE_MAX){
				turnLeft(delta);
			}
			break;
		case NORTH:
			if(getAngle() < WorldSpatial.NORTH_DEGREE){
				turnLeft(delta);
			}
			break;
		case SOUTH:
			if(getAngle() < WorldSpatial.SOUTH_DEGREE){
				turnLeft(delta);
			}
			break;
		case WEST:
			if(getAngle() < WorldSpatial.WEST_DEGREE){
				turnLeft(delta);
			}
			break;

		default:
			break;
		}

	}

	/**
	 * Checks whether the car's state has changed or not, stops turning if it
	 *  already has.
	 */
	private void checkStateChange() {
		if(previousState == null){
			previousState = getOrientation();
		}
		else{
			if(previousState != getOrientation()){
				if(isTurningLeft){
					isTurningLeft = false;
				}
				if(isTurningRight){
					isTurningRight = false;
				}
				previousState = getOrientation();
			}
		}
	}

	/**
	 * Turn the car counter clock wise (think of a compass going counter clock-wise)
	 */
	private void applyLeftTurn(WorldSpatial.Direction orientation, float delta) {
		switch(orientation){
		case EAST:
			if(!getOrientation().equals(WorldSpatial.Direction.NORTH)){
				turnLeft(delta);
			}
			break;
		case NORTH:
			if(!getOrientation().equals(WorldSpatial.Direction.WEST)){
				turnLeft(delta);
			}
			break;
		case SOUTH:
			if(!getOrientation().equals(WorldSpatial.Direction.EAST)){
				turnLeft(delta);
			}
			break;
		case WEST:
			if(!getOrientation().equals(WorldSpatial.Direction.SOUTH)){
				turnLeft(delta);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Turn the car clock wise (think of a compass going clock-wise)
	 */
	private void applyRightTurn(WorldSpatial.Direction orientation, float delta) {
		switch(orientation){
		case EAST:
			if(!getOrientation().equals(WorldSpatial.Direction.SOUTH)){
				turnRight(delta);
			}
			break;
		case NORTH:
			if(!getOrientation().equals(WorldSpatial.Direction.EAST)){
				turnRight(delta);
			}
			break;
		case SOUTH:
			if(!getOrientation().equals(WorldSpatial.Direction.WEST)){
				turnRight(delta);
			}
			break;
		case WEST:
			if(!getOrientation().equals(WorldSpatial.Direction.NORTH)){
				turnRight(delta);
			}
			break;
		default:
			break;

		}
	}

	/**
	 * Check if you have a wall in front of you!
	 * @param orientation the orientation we are in based on WorldSpatial
	 * @param currentView what the car can currently see
	 * @return
	 */

	private boolean checkWallAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
		switch(orientation){
		case EAST:
			return checkEast(currentView);
		case NORTH:
			return checkNorth(currentView);
		case SOUTH:
			return checkSouth(currentView);
		case WEST:
			return checkWest(currentView);
		default:
			return false;

		}
	}

	/**
	 * Check if the wall is on your left hand side given your orientation
	 * @param orientation
	 * @param currentView
	 * @return
	 */

	private boolean checkFollowingWall(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {

		switch(orientation){
		case EAST:
			return checkNorth(currentView);
		case NORTH:
			return checkWest(currentView);
		case SOUTH:
			return checkEast(currentView);
		case WEST:
			return checkSouth(currentView);
		default:
			return false;
		}

	}

	/**
	 * Method below just iterates through the list and check in the correct coordinates.
	 * i.e. Given your current position is 10,10
	 * checkEast will check up to wallSensitivity amount of tiles to the right.
	 * checkWest will check up to wallSensitivity amount of tiles to the left.
	 * checkNorth will check up to wallSensitivity amount of tiles to the top.
	 * checkSouth will check up to wallSensitivity amount of tiles below.
	 */
	public boolean checkEast(HashMap<Coordinate, MapTile> currentView){
		// Check tiles to my right
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x+i, currentPosition.y));
			if(tile.getName().equals("Wall")){
				return true;
			}
		}
		return false;
	}

	public boolean checkWest(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to my left
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x-i, currentPosition.y));
			if(tile.getName().equals("Wall")){
				return true;
			}
		}
		return false;
	}

	public boolean checkNorth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to towards the top
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y+i));
			if(tile.getName().equals("Wall")){
				return true;
			}
		}
		return false;
	}

	public boolean checkSouth(HashMap<Coordinate,MapTile> currentView){
		// Check tiles towards the bottom
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= wallSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y-i));
			if(tile.getName().equals("Wall")){
				return true;
			}
		}
		return false;
	}
	
	private boolean checkTrapAhead(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView){
		switch(orientation){
		case EAST:
			return checkEastForTrap(currentView);
		case NORTH:
			return checkNorthForTrap(currentView);
		case SOUTH:
			return checkSouthForTrap(currentView);
		case WEST:
			return checkWestForTrap(currentView);
		default:
			return false;

		}
	}
	
	private boolean checkFollowingTrap(WorldSpatial.Direction orientation, HashMap<Coordinate, MapTile> currentView) {
		switch(orientation){
		case EAST:
			return checkNorthForTrap(currentView);
		case NORTH:
			return checkWestForTrap(currentView);
		case SOUTH:
			return checkEastForTrap(currentView);
		case WEST:
			return checkSouthForTrap(currentView);
		default:
			return false;
		}
	}
	
	public boolean checkEastForTrap(HashMap<Coordinate, MapTile> currentView){
		// Check tiles to my right
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= trapSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x+i, currentPosition.y));
			if(tile.getName().equals("Trap")){
				return true;
			}
		}
		return false;
	}

	public boolean checkWestForTrap(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to my left
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= trapSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x-i, currentPosition.y));
			if(tile.getName().equals("Trap")){
				return true;
			}
		}
		return false;
	}

	public boolean checkNorthForTrap(HashMap<Coordinate,MapTile> currentView){
		// Check tiles to towards the top
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= trapSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y+i));
			if(tile.getName().equals("Trap")){
				return true;
			}
		}
		return false;
	}

	public boolean checkSouthForTrap(HashMap<Coordinate,MapTile> currentView){
		// Check tiles towards the bottom
		Coordinate currentPosition = new Coordinate(getPosition());
		for(int i = 0; i <= trapSensitivity; i++){
			MapTile tile = currentView.get(new Coordinate(currentPosition.x, currentPosition.y-i));
			if(tile.getName().equals("Trap")){
				return true;
			}
		}
		return false;
	}
}
