package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a red escape hatch card.
 * 
 * @author cerridifebbo
 * @see EscapeHatchCard that is extended by this class.
 */

public class RedEscapeHatchCard extends EscapeHatchCard {

	private static final long serialVersionUID = -2455595422967351201L;

	/**
	 * With this method the player who uses this card does not escape. The
	 * position of the hatch is not more passable
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player who uses the card
	 * @param target
	 *            null.
	 * @param game
	 *            null
	 * @return null.
	 * @exception IllegalArgumentException
	 *                if player is null or if player is not an instance of
	 *                HumanPlayer
	 */
	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player != null && player instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) player;
			p.getPosition().setPassable(false);
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}
}
