package it.polimi.ingsw.cerridifebbo.model;

import java.io.File;

public interface SectorFactory {
	Sector createSector(int type, int raw, int column);
}
