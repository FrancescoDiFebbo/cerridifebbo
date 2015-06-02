package it.polimi.ingsw.cerridifebbo.model;

public class DefenseItemCard extends ItemCard {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1857948974335792760L;
	private static final String NAME = "Defense";

	@Override
	public Object performAction(Player player, Object target, Game game) {
		if (player != null && player instanceof HumanPlayer) {
			HumanPlayer p = (HumanPlayer) player;
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
