package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a secure sector.
 * 
 * @see Sector that is extended by this class
 * @author cerridifebbo
 *
 */
public class SecureSector extends Sector {

	private static final long serialVersionUID = 1L;

	/**
	 * This method is the constructor.Set the parameter passable true.
	 * 
	 * @see Sector constructor that is in invoked.
	 * 
	 * @author cerridifebbo
	 * @param row
	 *            the row of the sector
	 * @param column
	 *            the column of the sector
	 * 
	 */
	public SecureSector(int row, int column) {
		super(row, column, true);
	}

	/**
	 * This method controls if the player who enters in this sector has to draw
	 * a card from the deck.
	 * 
	 * @param player
	 *            the player that enters in the sector
	 * @param deck
	 *            the deck
	 * @return null
	 */
	@Override
	public Card playerEnters(Player player, Deck deck) {
		return null;
	}
}
