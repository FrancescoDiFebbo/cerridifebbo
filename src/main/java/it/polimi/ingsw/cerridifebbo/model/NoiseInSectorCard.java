package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a noise in sector card.
 * 
 * @author cerridifebbo
 * @see SectorCard that is extended by this class.
 */

public class NoiseInSectorCard extends SectorCard {

	/**
	 * This method set if the card contains an item
	 * 
	 * @author cerridifebbo
	 * @param item
	 *            true if contains an item, false otherwise
	 */
	NoiseInSectorCard(boolean item) {
		super(item);
	}

	/**
	 * With this method a the player who uses this card must declare his own
	 * position.If the card contains an item, the player takes an item card
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
		game.informPlayers(player, Sentence.NOISE_IN, player.getPosition());
		if (this.containsItem()) {
			return (ItemCard) game.getDeck().drawItemCard();
		} else {
			return null;
		}
	}
}
