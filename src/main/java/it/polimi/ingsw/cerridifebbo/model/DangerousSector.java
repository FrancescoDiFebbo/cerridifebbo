package it.polimi.ingsw.cerridifebbo.model;

public class DangerousSector extends Sector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6990464487915432347L;

	public DangerousSector(int raw, int column) {
		super(raw, column, true);
	}

	@Override
	public Card playerEnters(Player player, Deck deck) {

		if (player instanceof HumanPlayer) {
			HumanPlayer human = (HumanPlayer) player;
			if (human.hasSedatives()) {
				human.setSedatives(false);
				return null;
			}
		}
		return deck.drawSectorCard();
	}
}
