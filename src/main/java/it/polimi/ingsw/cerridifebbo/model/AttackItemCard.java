package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes an adrenaline card.
 * 
 * @author cerridifebbo
 * @see ItemCard that is extended by this class.
 */

public class AttackItemCard extends ItemCard {

	/**
	 * With this method the player who uses this card can attack. The player
	 * must be a human, if is the method throws an exception. The card is
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
			p.setRevealed();
			game.informPlayers(player, Sentence.ATTACK_CARD, null);
			game.updatePlayer(player, this, false);
			p.attack(game);
			p.deleteCard(this);
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
		return ItemCardName.ATTACK.getName();
	}
}
