package strategy;

public class StrategyFactory {
	
	public static StrategyFactory sharedInstance;
	
	//public TrapStrategy timeBestStrategy;
	
	//Lazy initialization
	public static StrategyFactory getSharedInstance() {
		if (sharedInstance == null) {
			sharedInstance = new StrategyFactory();
		}
		return sharedInstance;
	}
	
	public TrapStrategy getCompositeTrapStrategy() {
		CompositeTrapStrategy compositeStrategy = null;
		String className = "strategy.CompositeTrapStrategy";
		try {
			compositeStrategy = (CompositeTrapStrategy)Class.forName(className).newInstance();
			
			TrapStrategy healthStrategy = this.getHealthBestStrategy();
			TrapStrategy timeStrategy = this.getTimeBestStrategy();
			TrapStrategy controlStrategy = this.getControlBestStrategy();
			
			compositeStrategy.addStrategy(healthStrategy);
			compositeStrategy.addStrategy(timeStrategy);
			compositeStrategy.addStrategy(controlStrategy);
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return compositeStrategy;
	}
	
	public TrapStrategy getHealthBestStrategy() {
		TrapStrategy strategy = null;
		String className = "strategy.HealthBestStrategy";
		try {
			strategy = (HealthBestStrategy) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strategy;
	}
	
	public TrapStrategy getTimeBestStrategy() {
		TrapStrategy strategy = null;
		String className = "strategy.TimeBestStrategy";
		try {
			strategy = (TimeBestStrategy) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strategy;
	}
	
	public TrapStrategy getControlBestStrategy() {
		TrapStrategy strategy = null;
		String className = "strategy.ControlBestStrategy";
		try {
			strategy = (ControlBestStrategy) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strategy;
	}
	
	public DeadEndStrategy getCompositeDeadEndStrategy() {
		CompositeDeadEndStrategy compositeDeadEndStrategy = null;
		String className = "strategy.CompositeDeadEndStrategy";
		try {
			compositeDeadEndStrategy = (CompositeDeadEndStrategy)Class.forName(className).newInstance();
			
			DeadEndStrategy reverseStrategy = this.getReverseOutStrategy();
			DeadEndStrategy threePointStrategy = this.getThreePointTurnStrategy();
			DeadEndStrategy uTurnStrategy = this.getUturnStrategy();
			
			compositeDeadEndStrategy.addStrategy(uTurnStrategy);
			compositeDeadEndStrategy.addStrategy(reverseStrategy);
			compositeDeadEndStrategy.addStrategy(threePointStrategy);
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return compositeDeadEndStrategy;
	}
	
	public DeadEndStrategy getUturnStrategy() {
		DeadEndStrategy strategy = null;
		
		String className = "strategy.UturnStrategy";
		try {
			strategy = (DeadEndStrategy) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strategy;
		
	}
	
	public DeadEndStrategy getThreePointTurnStrategy() {
		DeadEndStrategy strategy = null;
		
		String className = "strategy.ThreePointTurnStrategy";
		try {
			strategy = (DeadEndStrategy) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strategy;
		
	}
	
	public DeadEndStrategy getReverseOutStrategy() {
		DeadEndStrategy strategy = null;
		
		String className = "strategy.ReverseOutStrategy";
		try {
			strategy = (DeadEndStrategy) Class.forName(className).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strategy;
		
	}
}
