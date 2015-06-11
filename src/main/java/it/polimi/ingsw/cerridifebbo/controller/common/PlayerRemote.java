package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.CharacterCard;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.io.Serializable;
import java.util.List;

public class PlayerRemote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CharacterCardRemote playerCard;
	private final SerializableArrayList<ItemCardRemote> ownCards;
	private final String pos;

	public PlayerRemote(Player player) {
		this.playerCard = new CharacterCardRemote(player.getPlayerCard());
		this.ownCards = new SerializableArrayList<PlayerRemote.ItemCardRemote>();
		for (Card item : player.getOwnCards()) {
			ownCards.add(new ItemCardRemote(item));
		}
		this.pos = player.getPosition().toString();
	}

	public CharacterCardRemote getPlayerCard() {
		return playerCard;
	}

	public List<ItemCardRemote> getOwnCards() {
		return ownCards;
	}

	public String getPos() {
		return pos;
	}

	public class CharacterCardRemote implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final String name;
		private final String race;

		public CharacterCardRemote(CharacterCard playerCard) {
			this.name = playerCard.getCharacterName();
			this.race = playerCard.getClass().getSimpleName();
		}

		public String getName() {
			return name;
		}

		public String getRace() {
			return race;
		}
	}

	public class ItemCardRemote implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final String name;

		public ItemCardRemote(Card itemCard) {
			this.name = itemCard.toString();
		}

		public String getName() {
			return name;
		}
	}
}
