package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote.SectorRemote;

public interface SectorButtonFactory {
	public SectorButton createSectorButton(SectorRemote temp, String name);
}
