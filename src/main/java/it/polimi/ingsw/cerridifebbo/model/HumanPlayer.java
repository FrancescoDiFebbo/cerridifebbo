package it.polimi.ingsw.cerridifebbo.model;

public class HumanPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2920770936102414367L;
	private boolean escaped;
	private boolean sedatives;

	public static final int HUMANMOVEMENT = 1;

	HumanPlayer(CharacterCard playerCard, Sector pos) {
		super(playerCard, pos, HUMANMOVEMENT);
		setEscaped(false);
	}

	public boolean isEscaped() {
		return escaped;
	}

	public void setEscaped(boolean escaped) {
		this.escaped = escaped;
	}

	public boolean hasSedatives() {
		return sedatives;
	}

	public void setSedatives(boolean sedatives) {
		this.sedatives = sedatives;
	}

	public void clear() {
		sedatives = false;
		setMaxMovement(HUMANMOVEMENT);
	}
}
