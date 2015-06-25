package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

/**
 * This class describes a human sector button.It extends the SectorButton class.
 * 
 * @see SectorButton
 * @author cerridifebbo
 *
 */
public class HumanSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color HUMAN_COLOR = Color.BLUE;

	/**
	 * This constructor calls the super constructor and sets the foreground
	 * color to HUMAN_COLOR.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the sector
	 */
	public HumanSectorButton(String label) {
		super(label);
		this.setForeground(HUMAN_COLOR);
	}

}