package it.polimi.ingsw.cerridifebbo.model;

public class Sector {
	private Coordinate coordinate;
	private boolean passable;
	private Player[] playerInside;
	
	public Sector (int raw, int column, boolean passable){
		coordinate = new Coordinate (raw, column);
		setPassable(passable);
	}
	
	public void playerEnters(Player player) {

	}

	public void playerLeaves(Player player) {

	}

	public boolean containsPlayer(Player player) {
		return false;
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}
}
