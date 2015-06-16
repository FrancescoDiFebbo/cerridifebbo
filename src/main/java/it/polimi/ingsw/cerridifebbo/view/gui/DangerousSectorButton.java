package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

/**
 * This class describes a dangerous sector button.It extends the SectorButton
 * class.
 * 
 * @see SectorButton
 * @author cerridifebbo
 *
 */
public class DangerousSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color DANGEROUS_COLOR = Color.DARK_GRAY;

	/**
	 * This constructor calls the super constructor and sets the foreground color
	 * to DANGEROUS_COLOR.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the sector
	 */
	public DangerousSectorButton(String label) {
		super(label);
		this.setForeground(DANGEROUS_COLOR);
	}

}
