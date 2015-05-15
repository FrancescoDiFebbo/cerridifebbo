package it.polimi.ingsw.cerridifebbo.model;

public class EscapeHatchSector extends Sector {

	public EscapeHatchSector(int raw, int column) {
		super(raw, column, true);
	}

	@Override
	public Card playerEnters(Deck deck) {
		return deck.drawEscapeHatchCard();
	}

}
