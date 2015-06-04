package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a noise in any sector card.
 * 
 * @author cerridifebbo
 * @see SectorCard that is extended by this class.
 */
public class NoiseAnySectorCard extends SectorCard {

	private static final long serialVersionUID = 6904887154489570312L;

	/**
	 * This method set if the card contains an item
	 * 
	 * @author cerridifebbo
	 * @param item
	 *            true if contains an item, false otherwise
	 */
	NoiseAnySectorCard(boolean containsItem) {
		super(containsItem);
	}

	/**
	 * With this method a the player who uses this card must declare one sector
	 * of the map.If the card contains an item, the player takes an item card
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player who uses this card
	 * @param target
	 *            null.
	 * @param game
	 *            the reference to the current game
	 * @exception IllegalArgumentException
	 *                if player is null
	 * @return if the card contains an item return an ItemCard, otherwise return
	 *         null
	 */
	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player == null) {
			throw new IllegalArgumentException("Player is null");
		}
		game.declareSector(player, null, false);
		if (this.containsItem()) {
			ItemCard itemCard = (ItemCard) game.getDeck().drawItemCard();
			if (itemCard != null) {
				itemCard.setTaken(true);
			}
			return itemCard;
		} else {
			return null;
		}
	}
}
