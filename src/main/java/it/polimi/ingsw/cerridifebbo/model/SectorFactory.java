package it.polimi.ingsw.cerridifebbo.model;

public interface SectorFactory {

	Sector createSector(int type, int raw, int column);

}
