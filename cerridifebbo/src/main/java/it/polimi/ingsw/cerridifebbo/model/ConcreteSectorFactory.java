package it.polimi.ingsw.cerridifebbo.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ConcreteSectorFactory implements SectorFactory {
	
	private final int NOSECTOR = 0;
	private final int SECURESECTOR = 1;
	private final int DANGEROUSSECTOR = 2;
	private final int HATCHSECTOR = 3;
	private final int ALIENSECTOR = 4;
	private final int HUMANSECTOR = 5;

	@Override
	public Sector createSector(int type, int raw, int column) {
		if (type == NOSECTOR)
			return new Sector(raw, column, false);
		if (type == SECURESECTOR)
			return new SecureSector(raw, column);
		if (type == DANGEROUSSECTOR)
			return new DangerousSector(raw, column);
		if (type == HATCHSECTOR)
			return new EscapeHatchSector(raw, column);
		if (type == ALIENSECTOR)
			return new AlienSector(raw, column);
		if (type == HUMANSECTOR)
			return new HumanSector(raw, column);
		return null;
	}
	
}