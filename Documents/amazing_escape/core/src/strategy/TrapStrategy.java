package strategy;

import controller.AIController;


public interface TrapStrategy {
	//public boolean checkTrapAhead(AIController ai);
	public boolean traverse(AIController ai);
	public boolean keepFollowingTrap(AIController ai);

}

