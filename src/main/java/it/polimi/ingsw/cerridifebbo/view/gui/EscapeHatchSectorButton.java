package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

/**
 * This class describes an escape hatch sector button.It extends the SectorButton
 * class.
 * 
 * @see SectorButton
 * @author cerridifebbo
 *
 */
public class EscapeHatchSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color ESCAPE_HATCH_COLOR_OK = Color.YELLOW;
	public static final Color ESCAPE_HATCH_COLOR_KO = Color.RED;

	/**
	 * This constructor calls the super constructor and sets the foreground color
	 * to ESCAPE_HATCH_COLOR.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the sector
	 */
	public EscapeHatchSectorButton(String label) {
		super(label);
		this.setForeground(ESCAPE_HATCH_COLOR_OK);
	}

}