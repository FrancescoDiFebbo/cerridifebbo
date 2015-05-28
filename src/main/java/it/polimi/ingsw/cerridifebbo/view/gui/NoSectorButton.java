package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.event.ActionEvent;

public class NoSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;

	public NoSectorButton(String label) {
		super(label);
		this.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		//no action performed for a no sector button
	}
}