package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Color;

public class EscapeHatchSectorButton extends SectorButton {

	private static final Color ESCAPE_HATCH_COLOR = Color.YELLOW;

	public EscapeHatchSectorButton(String label) {
		super(label);
		this.setForeground(ESCAPE_HATCH_COLOR);
	}
}