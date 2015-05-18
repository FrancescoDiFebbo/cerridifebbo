package it.polimi.ingsw.cerridifebbo.model;

public class AttackItemCard extends ItemCard {
	@Override
	public Object performAction(Player target, Game game) {
		target.deleteCard(this);
		return null;

	}
}
