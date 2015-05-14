package it.polimi.ingsw.cerridifebbo.model;

public class AlienSector extends Sector {

	public AlienSector(int raw, int column) {
		super(raw, column, false);
	}

	@Override
	public Card playerEnters(Deck deck) {
		return null;
	}

}
