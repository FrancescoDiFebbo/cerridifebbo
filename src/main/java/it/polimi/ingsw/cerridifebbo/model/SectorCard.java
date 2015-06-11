package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a generic Sector card.
 * 
 * @author cerridifebbo
 * @see card that is implemented by this class.
 */
public abstract class SectorCard implements Card {

	private final boolean containsItem;

	/**
	 * This method set if the card contains an item
	 * 
	 * @author cerridifebbo
	 * @param item
	 *            true if contains an item, false otherwise
	 */
	SectorCard(boolean item) {
		this.containsItem = item;
	}

	/**
	 * This method return if the card is taken or not
	 * 
	 * @author cerridifebbo
	 * @return if the card contains item true, otherwise false
	 */
	public boolean containsItem() {
		return containsItem;
	}

}
