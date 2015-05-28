package it.polimi.ingsw.cerridifebbo.model;

public class NoiseInSectorCard extends SectorCard {

	NoiseInSectorCard(boolean item) {
		super(item);
	}

	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player == null) {
			throw new IllegalArgumentException("Player is null");
		}
		game.declareSector(player, (Sector) player.getPosition(), false);
		if (this.containsItem()) {
			ItemCard itemCard = (ItemCard) game.getDeck().drawItemCard();
			if (itemCard != null) {
				itemCard.setTaken(true);
			}
			return itemCard;
		} else {
			return null;
		}
	}
}
