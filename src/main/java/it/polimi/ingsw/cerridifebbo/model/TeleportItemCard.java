package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.model.Game.Sentence;

/**
 * This class describes a teleport card.
 * 
 * @author cerridifebbo
 * @see ItemCard that is extended by this class.
 */
public class TeleportItemCard extends ItemCard {

	private static final long serialVersionUID = 8890754143423061845L;
	private static final String NAME = "Teleport";

	/**
	 * With this method the player who uses this card returns in the human
	 * sector.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player who uses the card
	 * @param target
	 *            null
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
			p.setPosition(game.getMap().getHumanSector());
			p.deleteCard(this);
			p.setRevealed();
			game.informPlayers(player, Sentence.TELEPORT_CARD, null);
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
