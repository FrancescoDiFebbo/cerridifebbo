package it.polimi.ingsw.cerridifebbo.model;

public class EscapeHatchSector extends Sector {

	public EscapeHatchSector(int row, int column) {
		super(row, column, true);
	}

	@Override
	public Card playerEnters(Player player, Deck deck) {
		return deck.drawEscapeHatchCard();
	}
}
