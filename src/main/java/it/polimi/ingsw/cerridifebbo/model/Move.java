package it.polimi.ingsw.cerridifebbo.model;

/**
 * The Class Move represents an action that the player wants to perform.
 *
 * @author cerridifebbo
 */
public class Move {

	/** The Constant MOVEMENT. */
	public static final String MOVEMENT = "MOVEMENT";

	/** The Constant ATTACK. */
	public static final String ATTACK = "ATTACK";

	/** The Constant USEITEMCARD. */
	public static final String USEITEMCARD = "USEITEMCARD";

	/** The Constant FINISH. */
	public static final String FINISH = "FINISH";

	/** The Constant TIMEFINISHED. */
	public static final String TIMEFINISHED = "TIMEFINISHED";

	/** The Constant SECTOR. */
	public static final String SECTOR = "SECTOR";

	/** The Constant DELETECARD. */
	public static final String DELETECARD = "DELETECARD";

	/** The action of the move. */
	private final String action;

	/** The target of the move. */
	private final String target;

	/**
	 * Instantiates a new move.
	 *
	 * @param action
	 *            the action
	 * @param target
	 *            the target
	 */
	public Move(String action, String target) {
		this.action = action;
		this.target = target == null ? null : target.trim();
	}

	/**
	 * Gets the action of the move.
	 *
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Gets the target of the move.
	 *
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}
}
