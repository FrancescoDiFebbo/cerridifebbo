package it.polimi.ingsw.cerridifebbo.model;

/**
 * The Class HumanPlayer adds human characteristics to the player.
 *
 * @see Player
 * @author cerridifebbo
 */
public class HumanPlayer extends Player {

	/** The Constant HUMANMOVEMENT. */
	public static final int HUMANMOVEMENT = 1;

	/** Indicates if human is escaped by hatch. */
	private boolean escaped;

	/** Indicate if human has used sedatives in his turn. */
	private boolean sedatives;

	/**
	 * Instantiates a new human player.
	 *
	 * @param playerCard
	 *            the player card
	 * @param pos
	 *            the position
	 */
	HumanPlayer(CharacterCard playerCard, Sector pos) {
		super(playerCard, pos, HUMANMOVEMENT);
		setEscaped(false);
	}

	/**
	 * Checks if the human is escaped.
	 *
	 * @return true, if is escaped
	 */
	public boolean isEscaped() {
		return escaped;
	}

	/**
	 * Sets if the human is escaped.
	 *
	 * @param escaped
	 *            the new escaped
	 */
	public void setEscaped(boolean escaped) {
		this.escaped = escaped;
	}

	/**
	 * Checks if human has used sedatives in his turn.
	 *
	 * @return true, if successful
	 */
	public boolean hasSedatives() {
		return sedatives;
	}

	/**
	 * Sets if human has used sedatives in his turn.
	 *
	 * @param sedatives
	 *            the new sedatives
	 */
	public void setSedatives(boolean sedatives) {
		this.sedatives = sedatives;
	}

	/**
	 * Clears the statistics of the player.
	 */
	public void clear() {
		sedatives = false;
		setMaxMovement(HUMANMOVEMENT);
	}
}
