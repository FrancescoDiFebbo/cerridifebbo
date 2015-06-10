package it.polimi.ingsw.cerridifebbo.model;

public class HumanSector extends Sector {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3056671765995518988L;

	public HumanSector(int row, int column) {
		super(row, column, false);
	}

	@Override
	public Card playerEnters(Player player, Deck deck) {
		return null;
	}

}
