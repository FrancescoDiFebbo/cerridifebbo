package it.polimi.ingsw.cerridifebbo.model;

public class NoiseAnySectorCard extends SectorCard {

	NoiseAnySectorCard(boolean containsItem) {
		super(containsItem);
	}

	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player == null) {
			throw new IllegalArgumentException("Player is null");
		}
		game.declareSector(player, null, false);
		if (this.containsItem()) {
			return game.getDeck().drawItemCard();
		} else {
			return null;
		}
	}
}
