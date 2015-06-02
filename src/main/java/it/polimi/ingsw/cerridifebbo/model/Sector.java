package it.polimi.ingsw.cerridifebbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Sector implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7531375122364670820L;
	private Coordinate coordinate;
	private boolean passable;
	private Sector north;
	private Sector south;
	private Sector northEast;
	private Sector southEast;
	private Sector northWest;
	private Sector southWest;

	public Sector(int row, int column, boolean passable) {
		coordinate = new Coordinate(row, column);
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

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public List<Sector> getAdjacentSectors() {
		List<Sector> list = new ArrayList<Sector>();
		if (north != null) {
			list.add(north);
		}
		if (northEast != null) {
			list.add(northEast);
		}
		if (southEast != null) {
			list.add(southEast);
		}
		if (south != null) {
			list.add(south);
		}
		if (southWest != null) {
			list.add(southWest);
		}
		if (northWest != null) {
			list.add(northWest);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Sector> getReachableSectors(int radius) {
		List<Sector> list = new ArrayList<Sector>();
		for (Sector sector : getAdjacentSectors()) {
			if (sector.isPassable()) {
				list.add(sector);
			}
		}
		for (int i = 0; i < radius - 1; i++) {
			List<Sector> temp = (ArrayList<Sector>) ((ArrayList<Sector>) list).clone();
			for (Sector sector : temp) {
				for (Sector adjacentSector : sector.getAdjacentSectors()) {
					checkAdjacentSector(list, adjacentSector);
				}
			}
		}
		return list;
	}

	private void checkAdjacentSector(List<Sector> list, Sector adjacentSector) {
		if (adjacentSector.isPassable() && !list.contains(adjacentSector) && !adjacentSector.equals(this))
			list.add(adjacentSector);
	}

	public abstract Card playerEnters(Player player, Deck deck);

	@Override
	public String toString() {
		char column = (char) (coordinate.getColumn() + 'A');
		String row = Integer.toString(coordinate.getRow() + 1);
		if (coordinate.getRow() < 9)
			return column + "0" + row;
		return column + row;
	}
}
