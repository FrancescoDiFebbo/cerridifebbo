package it.polimi.ingsw.cerridifebbo.model;

public class TeleportItemCard extends ItemCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String NAME = "Teleport";

	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player != null && player instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) player;
			p.setPosition(game.getMap().getHumanSector());
			p.deleteCard(this);
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}

	@Override
	public String toString() {
		return NAME;
	}
}
