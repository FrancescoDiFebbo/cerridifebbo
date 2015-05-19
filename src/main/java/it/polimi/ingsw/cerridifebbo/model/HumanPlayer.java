package it.polimi.ingsw.cerridifebbo.model;

public class HumanPlayer extends Player {

	private boolean escaped;
	public static final int HUMANMOVEMENT=1;

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
}
