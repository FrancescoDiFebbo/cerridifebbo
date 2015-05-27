package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.model.Player;
import javax.swing.JPanel;

public class CardsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 455;
	public static final int HEIGHT = 100;

	public CardsPanel(Player player) {

		if (player.getOwnCards() != null) {
			for (int i = 0; i < player.getOwnCards().size(); i++)
				add(new CardButton("card.toString"));
		}
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphic.BACKGROUND_COLOR);
	}
}
