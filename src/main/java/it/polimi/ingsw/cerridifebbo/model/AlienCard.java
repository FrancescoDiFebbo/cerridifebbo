package it.polimi.ingsw.cerridifebbo.model;

public class AlienCard extends CharacterCard {

	AlienCard(String characterName) {
		super(characterName);
	}
	
	@Override
	public Object performAction(Player target, Game game) {
		if (target != null) {
			try {
				throw new Exception("Target is not considered");
			} catch (Exception e) {
			}
		}
		return new AlienPlayer(this, game.getMap().getAlienSector());
		
	}

}
