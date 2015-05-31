package it.polimi.ingsw.cerridifebbo.model;

public class SpotlightItemCard extends ItemCard {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String NAME = "Spotlight";
	
	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player == null || !(player instanceof HumanPlayer)) {
			throw new IllegalArgumentException("Player not valid");
		} else if (target == null || !(target instanceof Sector)){
			throw new IllegalArgumentException("Target not valid");
		}
		HumanPlayer p = (HumanPlayer) player;
		game.declareSector(player, (Sector) target, true);
		p.deleteCard(this);
		return null;
	}

	@Override
	public String toString() {
		return NAME;
	}
}
