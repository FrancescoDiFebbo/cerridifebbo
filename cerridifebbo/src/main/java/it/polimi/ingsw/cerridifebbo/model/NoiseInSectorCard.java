package it.polimi.ingsw.cerridifebbo.model;

public class NoiseInSectorCard extends SectorCard {

	NoiseInSectorCard(boolean item) {
		super(item);
	}

	@Override
	public Object performAction(Player target, Game game) {
		if (target == null || !(target instanceof Player)) {
			throw new IllegalArgumentException("Target is not a Player");
		}
		Player player = (Player) target;
		game.declareSector(player, player.getPosition());
		return null;
	}
}
