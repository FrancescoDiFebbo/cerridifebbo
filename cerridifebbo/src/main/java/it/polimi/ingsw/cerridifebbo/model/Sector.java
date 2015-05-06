package it.polimi.ingsw.cerridifebbo.model;

public class Sector {
	private Coordinate coordinate;
	private boolean passable;
	private Player[] playerInside;
	

	public void playerEnters(Player player) {

	}

	public void playerLeaves(Player player) {

	}

	public boolean containsPlayer(Player player) {
		return false;
	}
}
