package it.polimi.ingsw.cerridifebbo.model;

public class HumanCard extends CharacterCard {

	HumanCard(String characterName) {
		super(characterName);
	}
	
	@Override
	public Object performAction(Object target, Map map) {
		if (target != null) {
			try {
				throw new Exception("Target is not considered");
			} catch (Exception e) {
			}
		}
		return new HumanPlayer(this, map.getHumanSector());		
	}
}
