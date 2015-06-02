package it.polimi.ingsw.cerridifebbo.model;

public class HumanCard extends CharacterCard {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3437849611858239415L;

	HumanCard(String characterName) {
		super(characterName);
	}

	@Override
	public Object performAction(Player player, Object target, Game game) {
		return new HumanPlayer(this, game.getMap().getHumanSector());
	}
}
