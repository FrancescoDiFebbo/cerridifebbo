package it.polimi.ingsw.cerridifebbo.model;

public class Move {
	public static final String MOVEMENT = "MOVEMENT";
	public static final String ATTACK = "ATTACK";
	public static final String USEITEMCARD = "USEITEMCARD";
	public static final String FINISH = "FINISH";
	public static final String TIMEFINISHED = "TIMEFINISHED";
	
	private final String action;
	private final Object target;
	
	public String getAction() {
		return action;
	}

	public Object getTarget() {
		return target;
	}

	public Move(String action, Object target) {
		this.action = action;
		this.target = target;
	}
}
