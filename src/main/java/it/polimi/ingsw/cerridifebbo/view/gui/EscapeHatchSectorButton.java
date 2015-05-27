package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class EscapeHatchSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color ESCAPE_HATCH_COLOR = Color.YELLOW;

	public EscapeHatchSectorButton(String label) {
		super(label);
		this.setForeground(ESCAPE_HATCH_COLOR);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		System.out.println("button clicked!" + this.getName());
		if (!isPressed()) {
			setPressed(true);
			setForeground(SectorButton.PRESSED_BUTTON);
		} else {
			setPressed(false);
			setForeground(ESCAPE_HATCH_COLOR);
		}
	}
}