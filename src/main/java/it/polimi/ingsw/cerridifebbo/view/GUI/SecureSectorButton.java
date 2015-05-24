package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Color;

public class SecureSectorButton extends SectorButton {

	private static final Color SECURE_COLOR = Color.GRAY;

	public SecureSectorButton(String label) {
		super(label);
		this.setForeground(SECURE_COLOR);
	}
}
