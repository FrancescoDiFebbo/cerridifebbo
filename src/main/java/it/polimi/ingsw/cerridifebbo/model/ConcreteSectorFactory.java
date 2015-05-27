package it.polimi.ingsw.cerridifebbo.model;

public class ConcreteSectorFactory implements SectorFactory {

	private static final int SECURESECTOR = 1;
	private static final int DANGEROUSSECTOR = 2;
	private static final int HATCHSECTOR = 3;
	private static final int ALIENSECTOR = 4;
	private static final int HUMANSECTOR = 5;

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