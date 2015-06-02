package it.polimi.ingsw.cerridifebbo.model;

public class NoiseAnySectorCard extends SectorCard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6904887154489570312L;

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
