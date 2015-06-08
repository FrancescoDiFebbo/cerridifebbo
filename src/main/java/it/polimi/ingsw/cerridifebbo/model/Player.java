package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.model.Game.Sentence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Player implements Serializable {

	private static final long serialVersionUID = 7274673996298525756L;
	private static final int MAX_CARDS = 3;
	private CharacterCard playerCard;
	private List<Card> ownCards;
	private Sector pos;
	private int maxMovement;
	private boolean alive;
	private boolean revealed;

	Player(CharacterCard playerCard, Sector pos, int maxMovement) {
		this.setPlayerCard(playerCard);
		this.pos = pos;
		this.maxMovement = maxMovement;
		this.ownCards = new ArrayList<Card>();
		this.alive = true;
		this.revealed = false;

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

	public void kill(Game game) {
		this.alive = false;
		setRevealed();
		game.inform(this, Sentence.KILLED, null);
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

	public List<Card> getOwnCards() {
		return ownCards;
	}

	public void addCard(Card card) {
		ownCards.add(card);
		((ItemCard) card).setTaken(true);
	}

	public void deleteCard(Card card) {
		ownCards.remove(card);
		((ItemCard) card).setTaken(false);
	}

	public boolean attack(Game game) {
		this.setRevealed();
		game.inform(this, Sentence.ATTACK, getPosition());		
		boolean humanEaten = false;
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (player.getPosition() == this.getPosition() && player != this && !hasDefenseCard(game, player)) {
				player.kill(game);
				humanEaten = isHumanEaten(humanEaten, player);
			}
		}
		return humanEaten;
	}

	private boolean isHumanEaten(boolean humanEaten, Player player) {
		if (player instanceof HumanPlayer)
			return true;
		return humanEaten;
	}

	private boolean hasDefenseCard(Game game, Player player) {
		if (player instanceof HumanPlayer) {
			for (Card card : player.getOwnCards()) {
				if (card instanceof DefenseItemCard) {
					card.performAction(player, null, game);
					return true;
				}
			}
		}
		return false;
	}

	public boolean movement(Sector destination, Game game) {
		if (getPosition().getReachableSectors(getMaxMovement()).contains(destination)) {
			setPosition(destination);
			game.sendToPlayer(this, "You moved to " + getPosition());
			Card sectorCard = destination.playerEnters(this, game.getDeck());
			Card itemCard = null;
			if (sectorCard != null) {
				itemCard = (Card) sectorCard.performAction(this, null, game);
				if (itemCard != null) {
					game.sendToPlayer(this, "You received " + itemCard + " card");
					this.addCard(itemCard);
				}
			}
			game.updatePlayer(this, itemCard, true);
			return true;
		}
		return false;
	}

	public boolean isRevealed() {
		return revealed;
	}

	public void setRevealed() {
		this.revealed = true;
	}

}
