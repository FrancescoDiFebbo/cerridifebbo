package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a generic Item card.
 * 
 * @author cerridifebbo
 * @see card that is implemented by this class.
 */

public abstract class ItemCard implements Card {

	boolean taken = false;

	/**
	 * This method return if the card is taken or not
	 * 
	 * @author cerridifebbo
	 * @return if the card is taken true, otherwise false
	 */
	public boolean isTaken() {
		return taken;
	}

	/**
	 * @author cerridifebbo
	 * @param taken
	 */
	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	/**
	 * @author cerridifebbo
	 * @return the name of the card.
	 */
	@Override
	public abstract String toString();
}
