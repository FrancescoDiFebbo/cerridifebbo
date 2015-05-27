package it.polimi.ingsw.cerridifebbo.model;

public class AlienCard extends CharacterCard {

	AlienCard(String characterName) {
		super(characterName);
	}
	
	@Override
	public Object performAction(Player player, Object target, Game game) {
		return new AlienPlayer(this, game.getMap().getAlienSector());	
	}

}
