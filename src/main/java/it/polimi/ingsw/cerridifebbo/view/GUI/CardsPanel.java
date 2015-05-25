package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Color;
import java.awt.Panel;

import it.polimi.ingsw.cerridifebbo.model.User;

import javax.swing.JPanel;

public class CardsPanel extends JPanel {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 100;

	public CardsPanel() {
		add(new CardButton("Sedatives"));
		add(new CardButton("adrenaline"));
		add(new CardButton(CardButton.NO_CARD));
		setSize(WIDTH, HEIGHT);
		setBackground(MainWindow.BACKGROUND_COLOR);
	}

}
