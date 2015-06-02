package it.polimi.ingsw.cerridifebbo.model;

public class AlienSector extends Sector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3526041625003527329L;

	public AlienSector(int row, int column) {
		super(row, column, false);
	}

	@Override
	public Card playerEnters(Player player, Deck deck) {
		return null;
	}
}
