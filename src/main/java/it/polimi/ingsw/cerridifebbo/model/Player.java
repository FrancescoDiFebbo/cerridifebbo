package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.server.User;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Player describes the characteristics of user in the game. A class
 * extending this adds more characteristics according to races.
 *
 * @author cerridifebbo
 */
public abstract class Player {

	/** The player card. */
	private CharacterCard playerCard;

	/** The own cards. */
	private List<Card> ownCards;

	/** The position. */
	private Sector pos;

	/** The max movement of the player. */
	private int maxMovement;

	/** Indicates if the player is alive. */
	private boolean alive;

	/** Indicates if the player is revealed in the game. */
	private boolean revealed;

	/**
	 * Instantiates a new player.
	 *
	 * @param playerCard
	 *            the player card
	 * @param pos
	 *            the position
	 * @param maxMovement
	 *            the max movement
	 */
	Player(CharacterCard playerCard, Sector pos, int maxMovement) {
		this.setPlayerCard(playerCard);
		this.pos = pos;
		this.maxMovement = maxMovement;
		this.ownCards = new ArrayList<Card>();
		this.alive = true;
		this.revealed = false;

	}

	/**
	 * Gets the player card.
	 *
	 * @return the player card
	 */
	public CharacterCard getPlayerCard() {
		return playerCard;
	}

	/**
	 * Sets the player card.
	 *
	 * @param playerCard
	 *            the new player card
	 */
	public void setPlayerCard(CharacterCard playerCard) {
		this.playerCard = playerCard;
	}

	/**
	 * Checks if the player is alive.
	 *
	 * @return true, if is alive
	 */
	public boolean isAlive() {
		return alive;
	}

	/**
	 * Kills the player.
	 *
	 * @param game
	 *            the game
	 */
	public void kill(Game game) {
		this.alive = false;
		setRevealed();
		game.informPlayers(this, Sentence.KILLED, null);
	}

	/**
	 * Gets the position of the player.
	 *
	 * @return the position
	 */
	public Sector getPosition() {
		return pos;
	}

	/**
	 * Sets the position of the player.
	 *
	 * @param pos
	 *            the new position
	 */
	public void setPosition(Sector pos) {
		this.pos = pos;
	}

	/**
	 * Gets the max movement of the player.
	 *
	 * @return the max movement
	 */
	public int getMaxMovement() {
		return maxMovement;
	}

	/**
	 * Sets the max movement of the player.
	 *
	 * @param maxMovement
	 *            the new max movement
	 */
	public void setMaxMovement(int maxMovement) {
		this.maxMovement = maxMovement;
	}

	/**
	 * Gets the item cards of the player.
	 *
	 * @return the own cards
	 */
	public List<Card> getOwnCards() {
		return ownCards;
	}

	/**
	 * Adds an item card in the player's deck.
	 *
	 * @param card
	 *            the card
	 */
	public void addCard(Card card) {
		ownCards.add(card);
		((ItemCard) card).setTaken(true);
	}

	/**
	 * Removes an item card from the player's deck.
	 *
	 * @param card
	 *            the card
	 */
	public void deleteCard(Card card) {
		ownCards.remove(card);
		((ItemCard) card).setTaken(false);
	}

	/**
	 * The player attacks in the current position.
	 *
	 * @param game
	 *            the game
	 * @return true, if a human has been killed
	 */
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

	/**
	 * Checks if a human has been eaten.
	 *
	 * @param humanEaten
	 *            the human eaten
	 * @param player
	 *            the player
	 * @return true, if is human eaten
	 */
	private boolean isHumanEaten(boolean humanEaten, Player player) {
		if (player instanceof HumanPlayer)
			return true;
		return humanEaten;
	}

	/**
	 * Checks if the player card owns a defense card.
	 *
	 * @param game
	 *            the game
	 * @param player
	 *            the player
	 * @return true, if successful
	 */
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

	/**
	 * The player moves to destination.
	 *
	 * @param destination
	 *            the destination
	 * @param game
	 *            the game
	 * @return true, if successful
	 */
	public boolean movement(Sector destination, Game game) {
		if (getPosition().getReachableSectors(getMaxMovement()).contains(destination)) {
			setPosition(destination);
			game.informPlayers(this, Sentence.MOVEMENT, pos);
			return true;
		}
		return false;
	}

	/**
	 * The player draws a sector card if he reaches a dangerous sector. If the
	 * sector card contains an item the player adds an item card in his deck.
	 *
	 * @param destination
	 *            the destination
	 * @param game
	 *            the game
	 */
	protected void drawSectorCard(Sector destination, Game game) {
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
				game.informPlayers(this, Sentence.RECEIVED_CARD, itemCard);
				this.addCard(itemCard);
			}
		}
		game.updatePlayer(this, itemCard, true);
	}

	/**
	 * Checks if the player is revealed.
	 *
	 * @return true, if is revealed
	 */
	public boolean isRevealed() {
		return revealed;
	}

	/**
	 * Sets if the player is revealed.
	 */
	public void setRevealed() {
		this.revealed = true;
	}

	/**
	 * Gets the player remote.
	 * 
	 * @see PlayerRemote
	 * @return the player remote
	 */
	public PlayerRemote getPlayerRemote() {
		return new PlayerRemote(this);
	}

}
