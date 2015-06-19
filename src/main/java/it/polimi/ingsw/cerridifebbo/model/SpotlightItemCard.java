package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardName;

public class SpotlightItemCard extends ItemCard {

	/**
	 * With this method the player who uses this card names a target sector. Any
	 * players (including the player who uses the card) that are in the target
	 * Sector or any of the six adjacent Sectors declare their positions. This
	 * card affects both Humans and Aliens.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player who uses the card
	 * @param target
	 *            the sector
	 * @param game
	 *            the reference to the current game
	 * @return null.
	 * @exception IllegalArgumentException
	 *                if player is null or if player is not an instance of
	 *                HumanPlayer and if target is null or target is not an
	 *                instance of Sector
	 */
	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player == null || !(player instanceof HumanPlayer)) {
			throw new IllegalArgumentException("Player not valid");
		} else if (target == null || !(target instanceof Sector)) {
			throw new IllegalArgumentException("Target not valid");
		}
		HumanPlayer p = (HumanPlayer) player;
		p.deleteCard(this);
		p.setRevealed();
		game.informPlayers(player, Sentence.SPOTLIGHT_CARD, (Sector) target);
		game.updatePlayer(player, this, false);
		return null;
	}

	/**
	 * @author cerridifebbo
	 * @return the name of the card
	 */
	@Override
	public String toString() {
		return ItemCardName.SPOTLIGHT.getName();
	}
}
