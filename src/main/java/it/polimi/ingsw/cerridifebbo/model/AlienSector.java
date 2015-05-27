package it.polimi.ingsw.cerridifebbo.model;

public class AlienSector extends Sector {

	public AlienSector(int row, int column) {
		super(row, column, false);
	}

	@Override
	public Card playerEnters(Player player, Deck deck) {
		return null;
	}
}
