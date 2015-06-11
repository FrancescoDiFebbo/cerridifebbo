package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.model.Game.Sentence;

/**
 * This class describes a noise in any sector card.
 * 
 * @author cerridifebbo
 * @see SectorCard that is extended by this class.
 */
public class NoiseAnySectorCard extends SectorCard {

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
	 *            sector
	 * @param game
	 *            the reference to the current game
	 * @exception IllegalArgumentException
	 *                if player is null and if target is null or target is not
	 *                an instance of Sector
	 * @return if the card contains an item return an ItemCard, otherwise return
	 *         null
	 */
	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player == null) {
			throw new IllegalArgumentException("Player is null");
		} else if (target == null || !(target instanceof Sector)) {
			throw new IllegalArgumentException("Sector not valid");
		}
		game.informPlayers(player, Sentence.NOISE_ANY, (Sector) target);
		if (this.containsItem()) {
			return (ItemCard) game.getDeck().drawItemCard();
		} else {
			return null;
		}
	}
}
