package it.polimi.ingsw.cerridifebbo.model;

import java.io.Serializable;

/**
 * This interface describes a generic card.
 * 
 * @author cerridifebbo
 */

public interface Card extends Serializable {

	/**
	 * @author cerridifebbo
	 * @param player
	 *            the player who uses the card
	 * @param target
	 *            the target of the card; it could be null or a Sector's object
	 * @param game
	 *            the reference to the current game
	 * @return null or a generic object. It depends on the type of card.
	 */
	public abstract Object performAction(Player player, Object target, Game game);

}
