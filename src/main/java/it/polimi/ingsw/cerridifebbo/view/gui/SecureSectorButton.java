package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

public class SecureSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color SECURE_COLOR = Color.LIGHT_GRAY;

	public SecureSectorButton(String label) {
		super(label);
		this.setForeground(SECURE_COLOR);
	}

}
