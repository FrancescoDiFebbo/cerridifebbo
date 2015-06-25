package it.polimi.ingsw.cerridifebbo.model;

/**
 * The Class GameState represents the FSM state of the game.
 *
 * @author cerridifebbo
 */
public abstract class GameState {

	/** The game containing the state */
	protected Game game;

	/**
	 * Instantiates a new game state.
	 *
	 * @param game
	 *            the game
	 */
	public GameState(Game game) {
		this.game = game;
	}

	/**
	 * Runs the state.
	 */
	public abstract void handle();
}
