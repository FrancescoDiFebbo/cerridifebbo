package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a silence card.
 * 
 * @author cerridifebbo
 * @see SectorCard that is extended by this class.
 */
public class SilenceCard extends SectorCard {

	/**
	 * This method set false if the card contains an item
	 * 
	 * @author cerridifebbo
	 * 
	 */
	SilenceCard() {
		super(false);
	}

	/**
	 * With this method the player who uses this card must announce silence in
	 * all sector
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            null
	 * @param target
	 *            null.
	 * @param game
	 *            null
	 * @return null.
	 * 
	 */
	@Override
	public Object performAction(Player player, Object target, Game game) {
		return null;
	}
}
