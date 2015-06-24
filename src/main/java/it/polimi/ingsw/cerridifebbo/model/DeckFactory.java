package it.polimi.ingsw.cerridifebbo.model;

/**
 * This interface describes a deck factory. It uses the abstract factory design
 * pattern.
 * 
 * @author cerridifebbo
 *
 */
public interface DeckFactory {

	/**
	 * This method creates a deck.
	 * 
	 * @author cerridifebbo
	 * @param numberOfPlayers
	 *            the number of players
	 * @return the new deck
	 */
	public Deck createDeck(int numberOfPlayers);

}
