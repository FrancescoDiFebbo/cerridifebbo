package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

/**
 * This class describes a secure sector button.It extends the SectorButton
 * class.
 * 
 * @see SectorButton
 * @author cerridifebbo
 *
 */
public class SecureSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color SECURE_COLOR = Color.LIGHT_GRAY;

	/**
	 * This constructor calls the super constructor and sets the foreground
	 * color to SECURE_COLOR.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the sector
	 */
	public SecureSectorButton(String label) {
		super(label);
		this.setForeground(SECURE_COLOR);
	}

}
