package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

public class DangerousSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color DANGEROUS_COLOR = Color.DARK_GRAY;

	public DangerousSectorButton(String label) {
		super(label);
		this.setForeground(DANGEROUS_COLOR);
	}

}
