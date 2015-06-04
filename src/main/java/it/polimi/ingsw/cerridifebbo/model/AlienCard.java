package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class describes an alien card.
 * 
 * @author cerridifebbo
 * @see CharacterCard that is extended by this class.
 */

public class AlienCard extends CharacterCard {

	private static final long serialVersionUID = 8866769745221525174L;

	/**
	 * This method set the characterName of the card with the parameter
	 * characterName
	 * 
	 * @author cerridifebbo
	 * @param characterName
	 *            the name of the character
	 */
	AlienCard(String characterName) {
		super(characterName);
	}

	/**
	 * With this method a new AlienPlayer is created.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            null
	 * @param target
	 *            null.
	 * @param game
	 *            the reference to the current game
	 * @return a new AlienPlayer
	 */
	@Override
	public Object performAction(Player player, Object target, Game game) {
		return new AlienPlayer(this, game.getMap().getAlienSector());
	}
}
