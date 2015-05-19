package it.polimi.ingsw.cerridifebbo.model;

public class SedativesItemCard extends ItemCard {
	@Override
	public Object performAction(Player target, Game game) {
		if (target != null && target instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) target;
			p.deleteCard(this);
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}
}
