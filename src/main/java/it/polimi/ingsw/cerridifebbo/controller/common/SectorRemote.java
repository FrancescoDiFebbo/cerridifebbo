package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.io.Serializable;

/**
 * The Class SectorRemote is a lightweight class for sharing from server to
 * client.
 *
 * @author cerridifebbo
 * @see Sector
 */
public class SectorRemote implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The type. */
	private final String type;

	/** The coordinate. */
	private final String coordinate;

	/** The passable. */
	private final Boolean passable;

	/**
	 * Instantiates a new sector remote.
	 *
	 * @param sector
	 *            the sector
	 */
	public SectorRemote(Sector sector) {
		this.type = sector.getClass().getSimpleName();
		this.coordinate = sector.toString();
		this.passable = sector.isPassable();
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Gets the coordinate.
	 *
	 * @return the coordinate
	 */
	public String getCoordinate() {
		return coordinate;
	}

	/**
	 * Checks if is passable.
	 *
	 * @return the boolean
	 */
	public Boolean isPassable() {
		return passable;
	}
}
