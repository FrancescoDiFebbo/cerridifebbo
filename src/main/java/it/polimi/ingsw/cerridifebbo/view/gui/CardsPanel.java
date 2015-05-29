package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.event.ActionListener;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Player;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class CardsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 455;
	public static final int HEIGHT = 100;

	public CardsPanel(Player player) {

		if (player.getOwnCards() != null) {
			for (int i = 0; i < player.getOwnCards().size(); i++) {
				add(new CardButton(player.getOwnCards().get(i).toString()));
			}
		}
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphics.BACKGROUND_COLOR);
	}

	public void addListenersToButton(ActionListener moveListener) {
		int nComp = getComponentCount();
		CardButton card;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				card = (CardButton) getComponent(i);
				card.addActionListener(moveListener);
			}

	}

	public void deleteListenersToButton(ActionListener moveListener) {
		int nComp = getComponentCount();
		CardButton card;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				card = (CardButton) getComponent(i);
				card.removeActionListener(moveListener);
			}

	}
}
