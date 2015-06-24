package it.polimi.ingsw.cerridifebbo.model;

import java.util.List;

/**
 * This interface describes a single type deck factory. It uses the factory
 * design pattern.
 * 
 * @author cerridifebbo
 *
 */

public interface SingleTypeDeckFactory {

	/**
	 * This method creates a list of cards.
	 * 
	 * @author cerridifebbo
	 * @param numberOfPlayers
	 *            the number of players
	 */
	public List<Card> createDeck(Integer numberOfPlayers);

}
