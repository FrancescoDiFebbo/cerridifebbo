package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.io.Serializable;

public class SectorRemote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String type;
	private final String coordinate;
	private final Boolean passable;

	public SectorRemote(Sector sector) {
		this.type = sector.getClass().getSimpleName();
		this.coordinate = sector.toString();
		this.passable = sector.isPassable();
	}

	public String getType() {
		return type;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public Boolean isPassable() {
		return passable;
	}
}
