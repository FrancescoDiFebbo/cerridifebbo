package it.polimi.ingsw.cerridifebbo.model;

public class HumanSector extends Sector {

	public HumanSector(int raw, int column) {
		super(raw, column, false);
	}

	@Override
	public Card playerEnters(Deck deck) {
		return null;
	}

}
