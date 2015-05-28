package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class DangerousSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color DANGEROUS_COLOR = Color.DARK_GRAY;

	public DangerousSectorButton(String label) {
		super(label);
		this.setForeground(DANGEROUS_COLOR);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		System.out.println("button clicked!" + ev.getActionCommand());
		if (!isPressed()) {
			setPressed(true);
			setForeground(SectorButton.PRESSED_BUTTON);
		} else {
			setPressed(false);
			setForeground(DANGEROUS_COLOR);
		}
	}
}
