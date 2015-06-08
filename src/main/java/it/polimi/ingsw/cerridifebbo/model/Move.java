package it.polimi.ingsw.cerridifebbo.model;

public class Move {
	public static final String MOVEMENT = "MOVEMENT";
	public static final String ATTACK = "ATTACK";
	public static final String USEITEMCARD = "USEITEMCARD";
	public static final String FINISH = "FINISH";
	public static final String TIMEFINISHED = "TIMEFINISHED";
	public static final String SECTOR = "SECTOR";
	public static final String DELETECARD = "DELETECARD";

	private final String action;
	private final String target;

	public Move(String action, String target) {
		this.action = action;
		this.target = target == null ? null : target.trim();
	}

	public String getAction() {
		return action;
	}

	public String getTarget() {
		return target;
	}
}
