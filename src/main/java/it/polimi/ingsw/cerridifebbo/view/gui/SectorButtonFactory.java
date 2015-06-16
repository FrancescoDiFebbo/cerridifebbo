package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote.SectorRemote;

/**
 * This interface descibes the factory design pattern. It has the method for
 * create SectorButton.
 * 
 * @author cerridifebbo
 *
 */
public interface SectorButtonFactory {
	/**
	 * This method creates a new SectorButton.
	 * 
	 * @author cerridifebbo
	 * @param temp
	 *            the sectorRemote
	 * @return the new SectorButton
	 */
	public SectorButton createSectorButton(SectorRemote temp);
}
