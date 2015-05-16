package it.polimi.ingsw.cerridifebbo.model;

public class SpotlightItemCard extends ItemCard {
	@Override
	public Object performAction(Object target, Map map) throws Exception {
		if (target instanceof Player && target != null) {
			Player p = (Player) target;
			if (p instanceof HumanPlayer) {
				p.setPos(map.getHumanSector());
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
