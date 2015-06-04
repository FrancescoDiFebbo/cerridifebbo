package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes a generic character card.
 * 
 * @author cerridifebbo
 * @see card that is implemented by this class.
 */
public abstract class CharacterCard implements Card {

	private static final long serialVersionUID = -5456628627751862631L;
	private final String characterName;

	/**
	 * This method set the characterName of the card with the param
	 * characterName
	 * 
	 * @author cerridifebbo
	 * @param characterName
	 *            the name of the character
	 */
	CharacterCard(String characterName) {
		this.characterName = characterName;
	}

	/**
	 * This method return the characterName of the card
	 * 
	 * @author cerridifebbo
	 * @return the name of the characterName
	 */
	public String getCharacterName() {
		return characterName;
	}
}
