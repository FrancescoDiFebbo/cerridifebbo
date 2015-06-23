package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a greenEscapeHatch card.
 * 
 * @author cerridifebbo
 * @see EscapeHatchCard that is extended by this class.
 */
public class GreenEscapeHatchCard extends EscapeHatchCard {

	/**
	 * With this method the player who uses this card escapes. The position of
	 * the hatch is not more passable
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
			p.setEscaped(true);
			p.getPosition().setPassable(false);
			p.setRevealed();
			game.informPlayers(p, Sentence.ESCAPED, p.getPosition());
			game.updatePlayer(p, null, false);
			game.updateHatch(p);
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}
}
