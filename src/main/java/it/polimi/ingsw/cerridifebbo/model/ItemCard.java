package it.polimi.ingsw.cerridifebbo.model;

public abstract class ItemCard implements Card {

	boolean taken = false;

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}
}
