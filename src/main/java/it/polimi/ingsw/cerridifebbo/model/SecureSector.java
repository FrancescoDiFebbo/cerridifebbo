package it.polimi.ingsw.cerridifebbo.model;

public class SecureSector extends Sector {

	public SecureSector(int raw, int column) {
		super(raw, column, true);

	}

	@Override
	public Card playerEnters(Deck deck) {
		return null;
	}

}