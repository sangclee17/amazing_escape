package strategy;

import controller.AIController;

public interface DeadEndStrategy {
	
	public boolean detectDeadEnd(AIController ai);
	public boolean outOfDeadEnd(AIController ai);
	public boolean isDeadEnd(AIController ai);
}
