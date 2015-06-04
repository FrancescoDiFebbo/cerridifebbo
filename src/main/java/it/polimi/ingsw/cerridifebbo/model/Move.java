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
	private final Sector target;
	private final Card selectedCard;

	public Move(String action, Sector target, Card selectedCard) {
		this.action = action;
		this.target = target;
		this.selectedCard = selectedCard;
	}

	public String getAction() {
		return action;
	}

	public Sector getTarget() {
		return target;
	}

	public Card getSelectedCard() {
		return selectedCard;
	}
}
