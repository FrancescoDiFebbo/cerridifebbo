package it.polimi.ingsw.cerridifebbo.model;

public class SecureSector extends Sector {

	public SecureSector(int row, int column) {
		super(row, column, true);
	}

	@Override
	public Card playerEnters(Player player, Deck deck) {
		return null;
	}
}
