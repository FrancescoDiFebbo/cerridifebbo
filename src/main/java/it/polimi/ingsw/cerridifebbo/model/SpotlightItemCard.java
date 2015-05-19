package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class SpotlightItemCard extends ItemCard {
	@Override
	public Object performAction(Player target, Game game) {
		if (target != null && target instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) target;
			for (User u : game.getUsers()) {
				if (u.getPlayer().equals(this)) {
					Move move = game.getMoveFromUser(u);
					if (move.getTarget() instanceof Sector) {
						Sector sectorSelected = (Sector) move.getTarget();
						ArrayList<Sector> adjacentSector = sectorSelected
								.getAdjacentSectors();
						adjacentSector.add(sectorSelected);
						for (Sector sector : adjacentSector) {
							for (User user : game.getUsers()) {
								if (user.getPlayer().getPosition()
										.equals(sector))
									game.declareSector(user.getPlayer(), sector);
							}
						}
					} else {
						// TODO exception when getMoveFromUser() is not a sector
					}
				}
				p.deleteCard(this);
			}
		} else {
			throw new IllegalArgumentException();
		}
		return null;
	}

}
