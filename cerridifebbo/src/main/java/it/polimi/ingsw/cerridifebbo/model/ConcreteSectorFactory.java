package it.polimi.ingsw.cerridifebbo.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ConcreteSectorFactory implements SectorFactory {

	private static final int NOSECTOR = 0;
	private static final int SECURESECTOR = 1;
	private static final int DANGEROUSSECTOR = 2;
	private static final int HATCHSECTOR = 3;
	private static final int ALIENSECTOR = 4;
	private static final int HUMANSECTOR = 5;

	@Override
	public Sector createSector(int type, int raw, int column) {
		if (type == NOSECTOR)
			return null;
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