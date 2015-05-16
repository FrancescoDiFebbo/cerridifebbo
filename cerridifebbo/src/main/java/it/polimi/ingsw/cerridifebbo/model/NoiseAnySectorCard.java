package it.polimi.ingsw.cerridifebbo.model;

public class NoiseAnySectorCard extends SectorCard {

	NoiseAnySectorCard(boolean containsItem) {
		super(containsItem);
	}

	@Override
	public Object performAction(Player target, Game game) {
		if (target == null || !(target instanceof Player)) {
			throw new IllegalArgumentException("Target is not a Player");
		}
		Player player = (Player) target;
		game.declareSector(player, null);
		return null;
	}
}
