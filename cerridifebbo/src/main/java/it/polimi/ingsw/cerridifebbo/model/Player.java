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

	public int getMaxMovement() {
		return maxMovement;
	}

	public Sector getSector() {
		return pos;
	}

	public Sector getPos() {
		return pos;
	}

	public void setPos(Sector pos) {
		this.pos = pos;
	}

	public void setMaxMovement(int maxMovement) {
		this.maxMovement = maxMovement;
	}

	public void attack(ArrayList<User> userList) throws Exception {
		for (User u : userList) {
			if (u.getPlayer().pos == this.pos) {
				boolean safe = false;
				for (Card card : this.getOwnCards()) {
					if (card instanceof AttackItemCard
							&& u.getPlayer() instanceof HumanPlayer) {
						this.deleteCard(card);
						safe = true;
						if (!safe)
							u.getPlayer().kill();
					}
				}
			}
		}
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

	public boolean movement(Sector destination) {
		if (pos.getReachableSectors(maxMovement).contains(destination)) {
			return true;
		}
		return false;
	}

}
