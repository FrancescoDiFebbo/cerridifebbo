package it.polimi.ingsw.cerridifebbo.model;

public class AttackItemCard extends ItemCard {
	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player != null && player instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) player;
			p.attack(game);
			p.deleteCard(this);
		} else {
			throw new IllegalArgumentException();
		}
		return null;

	}
}
