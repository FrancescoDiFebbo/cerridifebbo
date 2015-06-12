package it.polimi.ingsw.cerridifebbo.model;

/**
 * Interface of the factory pattern. It creates Sector.
 * 
 * @author cerridifebbo
 */
public interface SectorFactory {

	/**
	 * This method return the sector that is instance of HumanSector
	 * 
	 * @author cerridifebbo
	 * @param type
	 *            the type of sector
	 * @param row
	 *            the row of the new Sector
	 * @param column
	 *            the column of the new Sector
	 * @return a new Sector.
	 */
	Sector createSector(int type, int row, int column);

}
