package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

/**
 * This class describes an alien sector button.It extends the SectorButton
 * class.
 * 
 * @see SectorButton
 * @author cerridifebbo
 *
 */
public class AlienSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color ALIEN_COLOR = Color.GREEN;

	/**
	 * This constructor calls the super constructor and sets the foreground color
	 * to ALIEN_COLOR.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the sector
	 */
	public AlienSectorButton(String label) {
		super(label);
		this.setForeground(ALIEN_COLOR);
	}

}
