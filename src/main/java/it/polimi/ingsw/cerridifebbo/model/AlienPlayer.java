package it.polimi.ingsw.cerridifebbo.model;

/**
 * The Class AlienPlayer adds alien characteristics to the player.
 *
 * @see Player
 * @author cerridifebbo
 * 
 */
public class AlienPlayer extends Player {

	/** The Constant MOVEMENT_BEFORE_EATING. */
	private static final int MOVEMENT_BEFORE_EATING = 2;

	/** The Constant MOVEMENT_AFTER_EATING. */
	private static final int MOVEMENT_AFTER_EATING = 3;

	/**
	 * Instantiates a new alien player.
	 *
	 * @param playerCard
	 *            the player card
	 * @param pos
	 *            the position
	 */
	AlienPlayer(CharacterCard playerCard, Sector pos) {
		super(playerCard, pos, MOVEMENT_BEFORE_EATING);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.model.Player#attack(it.polimi.ingsw.cerridifebbo
	 * .model.Game)
	 */
	@Override
	public boolean attack(Game game) {
		if (super.attack(game) && getMaxMovement() == MOVEMENT_BEFORE_EATING) {
			setMaxMovement(MOVEMENT_AFTER_EATING);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.model.Player#movement(it.polimi.ingsw.
	 * cerridifebbo.model.Sector, it.polimi.ingsw.cerridifebbo.model.Game)
	 */
	@Override
	public boolean movement(Sector destination, Game game) {
		if (destination instanceof EscapeHatchSector) {
			return false;
		}
		return super.movement(destination, game);
	}
}
