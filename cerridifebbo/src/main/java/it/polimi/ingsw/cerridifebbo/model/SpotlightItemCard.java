package it.polimi.ingsw.cerridifebbo.model;

public class SpotlightItemCard extends ItemCard {
	@Override
	public Object performAction(Player target, Game game) throws Exception {
		if (target instanceof Player && target != null) {
			Player p = (Player) target;
			if (p instanceof HumanPlayer) {
				p.setPosition(game.getMap().getHumanSector());
			} else {
				throw new Exception("Alien can't use item cards");
			}
		}
		else{
			throw new Exception("Invalid target");
		}
		return null;
	}

}
