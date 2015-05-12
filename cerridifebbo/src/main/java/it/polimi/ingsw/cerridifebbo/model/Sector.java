package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public abstract class Sector {
	private Coordinate coordinate;
	private boolean passable;
	private ArrayList<Player> playerInside;

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
		playerInside.add(player);
	}

	public void playerLeaves(Player player) {
		playerInside.remove(player);
	}

	public boolean containsPlayer(Player player) {
		return playerInside.contains(player);
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public boolean isReachable(Player p) {
		return false;
	}

	@Override
	public String toString() {
		char column = (char) (coordinate.getColumn() + 'A');
		String raw = Integer.toString(coordinate.getRaw() + 1);
		if (coordinate.getRaw() < 9)
			return column + "0" + raw;
		return column + raw;
	}
}
