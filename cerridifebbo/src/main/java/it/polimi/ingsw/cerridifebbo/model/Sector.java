package it.polimi.ingsw.cerridifebbo.model;
public class Sector {
	private Coordinate coordinate;
	private boolean passable;
	private Player[] playerInside;

	private Sector north;
	private Sector south;
	private Sector northEast;
	private Sector southEast;
	private Sector northWest;
	private Sector southWest;

	public Sector(int raw, int column, boolean passable) {
		coordinate = new Coordinate(raw, column);
		setPassable(passable);
	}

	public void setNorthEast(Sector sector) {
		this.northEast = sector;
	}

	public void setNorthWest(Sector sector) {
		this.northWest = sector;
	}

	public void setSouthEast(Sector sector) {
		this.southEast = sector;
	}

	public void setSouthWest(Sector sector) {
		this.southWest = sector;
	}

	public void setNorth(Sector sector) {
		this.north = sector;
	}

	public void setSouth(Sector sector) {
		this.south = sector;
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
