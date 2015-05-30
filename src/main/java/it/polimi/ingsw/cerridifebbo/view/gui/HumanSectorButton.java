package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;

public class HumanSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;
	private static final Color HUMAN_COLOR = Color.BLUE;

	public HumanSectorButton(String label) {
		super(label);
		this.setForeground(HUMAN_COLOR);
	}

}