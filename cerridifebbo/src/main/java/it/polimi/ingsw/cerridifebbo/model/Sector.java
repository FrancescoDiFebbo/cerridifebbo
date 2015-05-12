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
		if (!isPassable()) {
			return false;
		}
		int maxMovement = p.getMaxMovement();
		Sector playerSector = p.getSector();

		return recursive(playerSector, maxMovement);

	}

	private boolean recursive(Sector playerSector, int maxMovement) {
		if (playerSector == null || maxMovement < 0
				|| !playerSector.isPassable())
			return false;
		if (playerSector.equals(this))
			return true;

		for (Sector sector : playerSector.getAdjacentSector()) {
			if (recursive(sector, maxMovement--)) {
				return true;
			}
		}
		return false;

	}

	public ArrayList<Sector> getAdjacentSector() {
		ArrayList<Sector> sectorList = new ArrayList<Sector>();
		sectorList.add(north);
		sectorList.add(northEast);
		sectorList.add(northWest);
		sectorList.add(south);
		sectorList.add(southEast);
		sectorList.add(southWest);
		return sectorList;
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
