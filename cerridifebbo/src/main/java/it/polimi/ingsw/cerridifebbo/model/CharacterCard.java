package it.polimi.ingsw.cerridifebbo.model;

public abstract class CharacterCard implements Card {

	private final String characterName;

	CharacterCard(String characterName) {
		this.characterName = characterName;
	}

	public String getCharacterName() {
		return characterName;
	}
	
	@Override
	public void performAction() {

	}
}
