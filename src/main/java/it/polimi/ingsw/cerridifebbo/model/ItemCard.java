package it.polimi.ingsw.cerridifebbo.model;

public abstract class ItemCard implements Card {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5753788244906874189L;
	boolean taken = false;

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}
	
	@Override
	public abstract String toString();
}
