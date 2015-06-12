package it.polimi.ingsw.cerridifebbo.model;

/**
 * This class create a new Sector. It implements the interface SectorFactory. It
 * is the concrete class that realizes the factory pattern.
 * 
 * @see SectorFactory that is implemented by this class.
 * @author cerridifebbo
 */
public class ConcreteSectorFactory implements SectorFactory {

	private static final int SECURESECTOR = 1;
	private static final int DANGEROUSSECTOR = 2;
	private static final int HATCHSECTOR = 3;
	private static final int ALIENSECTOR = 4;
	private static final int HUMANSECTOR = 5;

	/**
	 * This method return the new Sector. The type could be SECURESECTOR,
	 * DANGEROUSSECTOR, HATCHSECTOR, ALIENSECTOR, HUMANSECTOR
	 * 
	 * @author cerridifebbo
	 * @param type
	 *            the type of sector
	 * @param row
	 *            the row of the new Sector
	 * @param column
	 *            the column of the new Sector
	 * @return a new Sector.It is a null if the type is not one of the final
	 *         constants of this class.
	 */
	@Override
	public Sector createSector(int type, int row, int column) {
		switch (type) {
		case SECURESECTOR:
			return new SecureSector(row, column);
		case DANGEROUSSECTOR:
			return new DangerousSector(row, column);
		case HATCHSECTOR:
			return new EscapeHatchSector(row, column);
		case ALIENSECTOR:
			return new AlienSector(row, column);
		case HUMANSECTOR:
			return new HumanSector(row, column);
		default:
			return null;
		}
	}
}