package it.polimi.ingsw.cerridifebbo.model;

public class CharacterCard extends Card {	

	private final String race;
	private final String characterName;

	@Override
	public void performAction() {
		// TODO Auto-generated method stub

	}

	public CharacterCard(String race, String characterName){
		this.race = race;
		this.characterName = characterName;
	}
	
	public String getRace() {
		return race;
	}

	public String getCharacterName() {
		return characterName;
	}
}
