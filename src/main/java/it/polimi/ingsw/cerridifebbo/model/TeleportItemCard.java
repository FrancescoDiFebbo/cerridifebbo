package it.polimi.ingsw.cerridifebbo.model;

public class TeleportItemCard extends ItemCard {
	@Override
	public Object performAction(Player target, Game game) throws Exception {
		if (target != null && target instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) target;
			p.setPosition(game.getMap().getHumanSector());
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}
}
