package it.polimi.ingsw.cerridifebbo.model;

public abstract class Card {
	private boolean taken = false;
	
	public abstract void performAction();

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
}
