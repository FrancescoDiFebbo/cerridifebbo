package it.polimi.ingsw.cerridifebbo.model;

import java.util.List;

/**
 * This class describes a concrete deck factory. It implements the DeckFactory.
 * 
 * @see DeckFactory
 * @author cerridifebbo
 *
 */
public class ConcreteDeckFactory implements DeckFactory {

	/**
	 * This methos creates a deck.
	 * 
	 * @see getDeck
	 * @author cerridifebbo
	 * @param numberOfPlayers
	 *            the number of players
	 * @return a new deck
	 * @throws IllegalArgumentException
	 *             when numberOfPlayers is greater than
	 *             CharacterDeckFactory.MAN_PLAYERS or lesser than
	 *             CharacterDeckFactory.MIN_PLAYERS
	 */
	@Override
	public Deck createDeck(int numberOfPlayers) {
		if (numberOfPlayers < CharacterDeckFactory.MIN_PLAYERS || numberOfPlayers > CharacterDeckFactory.MAX_PLAYERS) {
			throw new IllegalArgumentException("Number of players between " + CharacterDeckFactory.MIN_PLAYERS + " and "
					+ CharacterDeckFactory.MAX_PLAYERS);
		}
		return getDeck(numberOfPlayers);
	}

	/**
	 * This methos creates a deck.
	 * 
	 * @see createDeck
	 * @author cerridifebbo
	 * @param numberOfPlayers
	 *            the number of players
	 * @return a new deck
	 */
	private Deck getDeck(int numberOfPlayers) {
		List<Card> characterCards = new CharacterDeckFactory().createDeck(numberOfPlayers);
		List<Card> sectorCards = new SectorDeckFactory().createDeck(null);
		List<Card> itemCards = new ItemDeckFactory().createDeck(null);
		List<Card> escapeHatchCards = new EscapeHatchDeckFactory().createDeck(null);

		return new Deck(characterCards, sectorCards, itemCards, escapeHatchCards);
	}
}
