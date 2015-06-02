package it.polimi.ingsw.cerridifebbo.model;

public abstract class CharacterCard implements Card {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5456628627751862631L;
	private final String characterName;

	CharacterCard(String characterName) {
		this.characterName = characterName;
	}

	public String getCharacterName() {
		return characterName;
	}
}
