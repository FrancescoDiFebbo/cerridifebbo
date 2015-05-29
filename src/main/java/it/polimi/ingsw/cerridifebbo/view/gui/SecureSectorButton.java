package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class SecureSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color SECURE_COLOR = Color.LIGHT_GRAY;

	public SecureSectorButton(String label) {
		super(label);
		this.setForeground(SECURE_COLOR);
	}

}
