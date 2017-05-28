package strategy;

import controller.AIController;

public interface DeadEndStrategy {
	
	public boolean outOfDeadEnd(AIController ai);
	public void getOutOfDeadEnd(AIController ai);
}
