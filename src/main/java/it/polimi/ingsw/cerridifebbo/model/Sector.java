package it.polimi.ingsw.cerridifebbo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class describes a generic sector. It must not be initialized.
 * 
 * @author cerridifebbo
 *
 */
public abstract class Sector implements Serializable {

	private static final long serialVersionUID = 1L;
	private Coordinate coordinate;
	private boolean passable;
	private Sector north;
	private Sector south;
	private Sector northEast;
	private Sector southEast;
	private Sector northWest;
	private Sector southWest;

	/**
	 * This method is the constructor.
	 * 
	 * @see Coordinate because a new Coordinate object is created
	 * 
	 * @author cerridifebbo
	 * @param row
	 *            the row of the sector
	 * @param column
	 *            the column of the sector
	 * @param passable
	 *            if a player can walk inside the sector
	 */
	public Sector(int row, int column, boolean passable) {
		coordinate = new Coordinate(row, column);
		setPassable(passable);
	}

	/**
	 * setter of northEast
	 */
	public void setNorthEast(Sector sector) {
		this.northEast = sector;
	}

	/**
	 * setter of northWest
	 */
	public void setNorthWest(Sector sector) {
		this.northWest = sector;
	}

	/**
	 * setter of southEast
	 */
	public void setSouthEast(Sector sector) {
		this.southEast = sector;
	}

	/**
	 * setter of southWest
	 */
	public void setSouthWest(Sector sector) {
		this.southWest = sector;
	}

	/**
	 * setter of north
	 */
	public void setNorth(Sector sector) {
		this.north = sector;
	}

	/**
	 * setter of south
	 */
	public void setSouth(Sector sector) {
		this.south = sector;
	}

	/**
	 * getter of passable
	 */
	public boolean isPassable() {
		return passable;
	}

	/**
	 * setter of passable
	 */
	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	/**
	 * getter of all the adjacent sector.
	 * 
	 * @return a list of all the adjacent sector.
	 */
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

	/**
	 * This method returns a list of all the reachable sectors from this sector
	 * in a radius of the parameter radius.The list does not contain sectors
	 * that are not passable.
	 * 
	 * @see checkAdjacentSector that is invoked by this method.
	 * 
	 * @return a list of all the reachable sector.
	 */
	public List<Sector> getReachableSectors(int radius) {
		List<Sector> list = new ArrayList<Sector>();
		for (Sector sector : getAdjacentSectors()) {
			if (sector.isPassable()) {
				list.add(sector);
			}
		}
		for (int i = 0; i < radius - 1; i++) {
			List<Sector> temp = new ArrayList<Sector>(list);
			for (Sector sector : temp) {
				for (Sector adjacentSector : sector.getAdjacentSectors()) {
					checkAdjacentSector(list, adjacentSector);
				}
			}
		}
		return list;
	}

	/**
	 * This method check in the list if the adjacent sector is passable and is
	 * not contained in the list. If all of the conditions are true the adjacent
	 * sector is added to the list.
	 * 
	 * @param list
	 *            the list of Sector
	 * @param adjacentSector
	 *            the sector controlled by this method
	 */
	private void checkAdjacentSector(List<Sector> list, Sector adjacentSector) {
		if (adjacentSector.isPassable() && !list.contains(adjacentSector) && !adjacentSector.equals(this))
			list.add(adjacentSector);
	}

	/**
	 * This method controls if the player who enters in this sector has to draw
	 * a card from the deck. Must be implemented in the class the implements
	 * this class.
	 * 
	 * @param player
	 *            the player that enters in the sector
	 * @param deck
	 *            the deck
	 * @return the card that is picked up by the player.It could be null
	 */
	public abstract Card playerEnters(Player player, Deck deck);

	/**
	 * This method override to string
	 *
	 * @return a string with the format "letter in capital"+"column number".
	 */
	@Override
	public String toString() {
		char column = (char) (coordinate.getColumn() + 'A');
		String row = Integer.toString(coordinate.getRow() + 1);
		if (coordinate.getRow() < 9)
			return column + "0" + row;
		return column + row;
	}
}
