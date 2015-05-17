package it.polimi.ingsw.cerridifebbo.model;

public class DefenseItemCard extends ItemCard {
	@Override
	public Object performAction(Player target, Game game) {
		target.deleteCard(this);
		return null;

	}
}
