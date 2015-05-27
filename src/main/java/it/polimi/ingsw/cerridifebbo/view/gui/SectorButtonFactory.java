package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.model.Sector;

public interface SectorButtonFactory {
	public SectorButton createSectorButton (Sector type, String name);
}
