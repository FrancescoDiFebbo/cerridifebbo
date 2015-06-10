package it.polimi.ingsw.cerridifebbo.model;

public class AlienPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6879251381186250824L;
	private static final int MOVEMENT_BEFORE_EATING = 2;
	private static final int MOVEMENT_AFTER_EATING = 3;

	AlienPlayer(CharacterCard playerCard, Sector pos) {
		super(playerCard, pos, MOVEMENT_BEFORE_EATING);
	}

	@Override
	public boolean attack(Game game) {
		if (super.attack(game) && getMaxMovement() == MOVEMENT_BEFORE_EATING) {
			setMaxMovement(MOVEMENT_AFTER_EATING);
			return true;
		}
		return false;
	}

	@Override
	public boolean movement(Sector destination, Game game) {
		if (destination instanceof EscapeHatchSector) {
			return false;
		}
		return super.movement(destination, game);
	}
}
