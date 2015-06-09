package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.model.Game.Sentence;

/**
 * This class describes a sedatives card.
 * 
 * @author cerridifebbo
 * @see ItemCard that is extended by this class.
 */
public class SedativesItemCard extends ItemCard {

	private static final long serialVersionUID = 2206994454307437871L;
	private static final String NAME = "Sedatives";

	/**
	 * With this method the player who uses this card does not draw a Dangerous
	 * Sector Card , even if he moves into a Dangerous Sector. The card is
	 * deleted from the player's cards.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player who uses the card
	 * @param target
	 *            null.
	 * @param game
	 *            the reference to the current game
	 * @return null.
	 * @exception IllegalArgumentException
	 *                if player is null or if player is not an instance of
	 *                HumanPlayer
	 */
	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player != null && player instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) player;
			p.setSedatives(true);
			p.deleteCard(this);
			p.setRevealed();
			game.informPlayers(player, Sentence.SEDATIVES_CARD, null);
			game.updatePlayer(player, this, false);
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}

	/**
	 * @author cerridifebbo
	 * @return the name of the card
	 */
	@Override
	public String toString() {
		return NAME;
	}
}
