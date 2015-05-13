package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public abstract class Sector {
	private Coordinate coordinate;
	private boolean passable;
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
		ArrayList<Sector> sectorList = new ArrayList<Sector>();
		sectorList.add(p.getPos());
		for (; maxMovement > 0; maxMovement--) {
			addAdjacentSector(sectorList);
			for (Sector s : sectorList) {
				if (s.toString().equals(toString()))
					return true;
			}
			System.out.println("sector list : " + sectorList);
		}

		return false;
	}

	private void addAdjacentSector(ArrayList<Sector> sectorList) {
		ArrayList<Sector> temp = new ArrayList<Sector>();
		for (Sector s : sectorList) {
			if (s.north != null && s.north.isPassable())
				temp.add(s.north);
			if (s.northEast != null && s.northEast.isPassable())
				temp.add(s.northEast);
			if (s.northWest != null && s.northWest.isPassable())
				temp.add(s.northWest);
			if (s.south != null && s.south.isPassable())
				temp.add(s.south);
			if (s.southEast != null && s.southEast.isPassable())
				temp.add(s.southEast);
			if (s.southWest != null && s.southWest.isPassable())
				temp.add(s.southWest);
		}
		for (Sector s : temp) {
			if (!sectorList.contains(s))
				sectorList.add(s);
		}
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
