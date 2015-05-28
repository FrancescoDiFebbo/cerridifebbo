package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class SecureSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color SECURE_COLOR = Color.LIGHT_GRAY;

	public SecureSectorButton(String label) {
		super(label);
		this.setForeground(SECURE_COLOR);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		System.out.println("button clicked!" + ev.getActionCommand());
		if (!isPressed()) {
			setPressed(true);
			setForeground(SectorButton.PRESSED_BUTTON);
		} else {
			setPressed(false);
			setForeground(SECURE_COLOR);
		}
	}
}
