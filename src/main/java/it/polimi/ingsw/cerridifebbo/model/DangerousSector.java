package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a dangerous sector.
 * 
 * @see Sector that is extended by this class
 * @author cerridifebbo
 *
 */
public class DangerousSector extends Sector {

	private static final long serialVersionUID = -6990464487915432347L;

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
	public DangerousSector(int raw, int column) {
		super(raw, column, true);
	}

	/**
	 * This method controls if the player who enters in this sector has to draw
	 * a card from the deck.
	 * 
	 * @param player
	 *            the player that enters in the sector
	 * @param deck
	 *            the deck
	 * @return the card picked up by the player.
	 */
	@Override
	public Card playerEnters(Player player, Deck deck) {

		if (player instanceof HumanPlayer) {
			HumanPlayer human = (HumanPlayer) player;
			if (human.hasSedatives()) {
				human.setSedatives(false);
				return null;
			}
		}
		return deck.drawSectorCard();
	}
}
