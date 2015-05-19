package it.polimi.ingsw.cerridifebbo.model;

public class AdrenalineItemCard extends ItemCard {
	private static final int ADRENALINEMOVEMENT = 2;

	@Override
	public Object performAction(Player target, Game game) {
		if (target != null && target instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) target;
			p.setMaxMovement(ADRENALINEMOVEMENT);
			p.deleteCard(this);
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}
}
