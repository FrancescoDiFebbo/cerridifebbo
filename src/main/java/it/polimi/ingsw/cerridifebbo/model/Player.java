package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public abstract class Player {

	private CharacterCard playerCard;
	private ArrayList<Card> ownCards;
	private Sector pos;
	private int maxMovement;
	private boolean alive;

	Player(CharacterCard playerCard, Sector pos, int maxMovement) {
		this.setPlayerCard(playerCard);
		this.pos = pos;
		this.maxMovement = maxMovement;
		this.ownCards = null;
		this.alive = true;

	}

	public CharacterCard getPlayerCard() {
		return playerCard;
	}

	public void setPlayerCard(CharacterCard playerCard) {
		this.playerCard = playerCard;
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

	public boolean attack(Game game) {
		boolean humanEaten = false;
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (player.getPosition() == this.getPosition() && player != this) {
				boolean safe = false;
				if (player instanceof HumanPlayer) {
					for (Card card : player.getOwnCards()) {
						if (card instanceof DefenseItemCard) {
							card.performAction(player, null, game);
							safe = true;
						}
					}
				}
				if (!safe) {
					player.kill();
					if (player instanceof HumanPlayer)
						humanEaten = true;
				}
			}
		}
		return humanEaten;
	}

	public boolean movement(Sector destination, Game game) {
		if (getPosition().getReachableSectors(getMaxMovement()).contains(destination)) {
			setPosition(destination);
			Card sectorCard = destination.playerEnters(this, game.getDeck());
			if (sectorCard != null){
				Card itemCard = (Card) sectorCard.performAction(this, null, game);
				this.addCard(itemCard);
			}
			return true;
		}
		return false;
	}

}
