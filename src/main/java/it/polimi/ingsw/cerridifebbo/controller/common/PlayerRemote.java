package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.CharacterCard;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.io.Serializable;
import java.util.List;

/**
 * The Class PlayerRemote is a lightweight class for sharing from server to
 * client.
 *
 * @author cerridifebbo
 */
public class PlayerRemote implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The player card. */
	private final CharacterCardRemote playerCard;

	/** The own cards. */
	private final SerializableArrayList<ItemCardRemote> ownCards;

	/** The position. */
	private final String pos;

	/** The race. */
	private final String race;

	/**
	 * Instantiates a new player remote.
	 *
	 * @param player
	 *            the player
	 */
	public PlayerRemote(Player player) {
		this.playerCard = new CharacterCardRemote(player.getPlayerCard());
		this.ownCards = new SerializableArrayList<ItemCardRemote>();
		for (Card item : player.getOwnCards()) {
			ownCards.add(new ItemCardRemote(item));
		}
		this.pos = player.getPosition().toString();
		this.race = player.getClass().getSimpleName();
	}

	/**
	 * Gets the race of player.
	 *
	 * @return the race
	 */
	public String getRace() {
		return race;
	}

	/**
	 * Gets the player card.
	 *
	 * @return the player card
	 */
	public CharacterCardRemote getPlayerCard() {
		return playerCard;
	}

	/**
	 * Gets the own cards.
	 *
	 * @return the own cards
	 */
	public List<ItemCardRemote> getOwnCards() {
		return ownCards;
	}

	/**
	 * Gets the position of player.
	 *
	 * @return the position
	 */
	public String getPos() {
		return pos;
	}

	/**
	 * The Class CharacterCardRemote is a lightweight class for sharing from
	 * server to client.
	 *
	 * @author cerridifebbo
	 */
	public class CharacterCardRemote implements Serializable {

		private static final long serialVersionUID = 1L;

		/** The name. */
		private final String name;

		/**
		 * Instantiates a new character card remote.
		 *
		 * @param playerCard
		 *            the player card
		 */
		public CharacterCardRemote(CharacterCard playerCard) {
			this.name = playerCard.getCharacterName();
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}
	}
}
