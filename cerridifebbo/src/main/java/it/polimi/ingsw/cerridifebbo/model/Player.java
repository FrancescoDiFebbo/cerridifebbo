package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public abstract class Player {

	private CharacterCard playerCard;
	private ArrayList<Card> ownCards;
	private Sector pos;
	private int maxMovement;
	private boolean alive;

	Player(CharacterCard playerCard, Sector pos, int maxMovement) {
		this.playerCard = playerCard;
		this.pos = pos;
		this.maxMovement = maxMovement;
		this.ownCards = null;
		this.alive = true;

	}

	public boolean isAlive() {
		return alive;
	}

	public void kill() {
		this.alive = false;
	}

	public Sector getPosition() {
		return pos;
	}

	public void setPosition(Sector pos) {
		this.pos = pos;
	}

	public int getMaxMovement() {
		return maxMovement;
	}

	public void setMaxMovement(int maxMovement) {
		this.maxMovement = maxMovement;
	}

	public ArrayList<Card> getOwnCards() {
		return ownCards;
	}

	public void addCard(Card card) {
		ownCards.add(card);
	}

	public void deleteCard(Card card) {
		ownCards.remove(card);
	}

	public abstract void attack(Game game) throws Exception;

	public abstract boolean movement(Sector destination);

}
