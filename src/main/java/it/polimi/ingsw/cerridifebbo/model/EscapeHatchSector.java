package it.polimi.ingsw.cerridifebbo.model;

public class EscapeHatchSector extends Sector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2926969345238245418L;

	public EscapeHatchSector(int row, int column) {
		super(row, column, true);
	}

	@Override
	public Card playerEnters(Player player, Deck deck) {
		return deck.drawEscapeHatchCard();
	}
}
