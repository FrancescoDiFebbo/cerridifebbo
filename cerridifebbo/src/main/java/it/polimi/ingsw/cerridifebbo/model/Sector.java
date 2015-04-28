package it.polimi.ingsw.cerridifebbo.model;

public class Sector {
	private Coordinate coordinate;
	private boolean passable;
	private Player[] playerInside;
	
	private Sector north;
	private Sector south;
	private Sector east;
	private Sector west;
	private Sector northEast;
	private Sector northWest;
	private Sector southEast;
	private Sector southWest;

	public void playerEnters(Player player) {

	}

	public void playerLeaves(Player player) {

	}

	public boolean containsPlayer(Player player) {
		return false;
	}
}
