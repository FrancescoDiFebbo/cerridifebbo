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
	private final String race;

	public PlayerRemote(Player player) {
		this.playerCard = new CharacterCardRemote(player.getPlayerCard());
		this.ownCards = new SerializableArrayList<ItemCardRemote>();
		for (Card item : player.getOwnCards()) {
			ownCards.add(new ItemCardRemote(item));
		}
		this.pos = player.getPosition().toString();
		this.race = player.getClass().getSimpleName();
	}

	public String getRace() {
		return race;
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

		public CharacterCardRemote(CharacterCard playerCard) {
			this.name = playerCard.getCharacterName();
		}

		public String getName() {
			return name;
		}

		public String getCharacterName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}
