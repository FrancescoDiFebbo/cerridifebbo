package it.polimi.ingsw.cerridifebbo.model;

import java.util.List;

public class ConcreteDeckFactory implements DeckFactory {

	@Override
	public Deck createDeck(int numberOfPlayers) {
		if (numberOfPlayers < CharacterDeckFactory.MIN_PLAYERS || numberOfPlayers > CharacterDeckFactory.MAX_PLAYERS) {
			throw new IllegalArgumentException("Number of players between " + CharacterDeckFactory.MIN_PLAYERS + " and "
					+ CharacterDeckFactory.MAX_PLAYERS);
		}
		return getDeck(numberOfPlayers);
	}

	private Deck getDeck(int numberOfPlayers) {
		List<Card> characterCards = new CharacterDeckFactory().createDeck(numberOfPlayers);
		List<Card> sectorCards = new SectorDeckFactory().createDeck(null);
		List<Card> itemCards = new ItemDeckFactory().createDeck(null);
		List<Card> escapeHatchCards = new EscapeHatchDeckFactory().createDeck(null);

		return new Deck(characterCards, sectorCards, itemCards, escapeHatchCards);
	}
}
