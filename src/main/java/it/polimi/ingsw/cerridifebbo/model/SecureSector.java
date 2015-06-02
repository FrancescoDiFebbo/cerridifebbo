package it.polimi.ingsw.cerridifebbo.model;

public class SecureSector extends Sector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -186662127462374807L;

	public SecureSector(int row, int column) {
		super(row, column, true);
	}

	@Override
	public Card playerEnters(Player player, Deck deck) {
		return null;
	}
}
