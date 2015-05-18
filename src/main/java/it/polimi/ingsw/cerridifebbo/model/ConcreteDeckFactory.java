package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

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
		ArrayList<Card> characterCards = new CharacterDeckFactory().createDeck(numberOfPlayers);
		ArrayList<Card> sectorCards = new SectorDeckFactory().createDeck();
		ArrayList<Card> itemCards = new ItemDeckFactory().createDeck();
		ArrayList<Card> escapeHatchCards = new EscapeHatchDeckFactory().createDeck();

		return new Deck(characterCards, sectorCards, itemCards, escapeHatchCards);
	}

}
