package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

public class EscapeHatchSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color ESCAPE_HATCH_COLOR = Color.YELLOW;

	public EscapeHatchSectorButton(String label) {
		super(label);
		this.setForeground(ESCAPE_HATCH_COLOR);
	}

}