package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.server.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Player{

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
		game.informPlayers(this, Sentence.KILLED, null);
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
		game.informPlayers(this, Sentence.ATTACK, getPosition());
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
			game.sendMessage(this, "You moved to " + getPosition());
			Card sectorCard = destination.playerEnters(this, game.getDeck());
			Sector target = null;
			if (sectorCard instanceof NoiseAnySectorCard) {
				do {
					target = game.getSector(this);
				} while (target == null);
			}
			Card itemCard = null;
			if (sectorCard != null) {
				itemCard = (Card) sectorCard.performAction(this, target, game);
				if (itemCard != null) {
					game.sendMessage(this, "You received " + itemCard + " card");
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

	public PlayerRemote getPlayerRemote() {
		return new PlayerRemote(this);
	}

}
