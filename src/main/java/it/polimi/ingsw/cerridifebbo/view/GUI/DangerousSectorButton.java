package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Color;

public class DangerousSectorButton extends SectorButton {

	private static final Color DANGEROUS_COLOR = Color.RED;

	public DangerousSectorButton(String label) {
		super(label);
		this.setForeground(Color.RED);
	}
}
