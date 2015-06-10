package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import it.polimi.ingsw.cerridifebbo.model.Player;

import javax.swing.JPanel;

public class CardsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 165;

	public CardsPanel(Player player, String playerRace) {
		setOpaque(true);
		setLayout(new FlowLayout());
		if (player.getOwnCards() != null) {
			for (int i = 0; i < player.getOwnCards().size(); i++) {
				add(new CardPanel(player.getOwnCards().get(i).toString(),
						playerRace));
			}
		}
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphics.BACKGROUND_COLOR);
	}

	public void addListenersToButton(ActionListener moveListener,
			boolean discard) {
		int nComp = getComponentCount();
		CardPanel card;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				card = (CardPanel) getComponent(i);
				card.getUse().setEnabled(true);
				card.getUse().addActionListener(moveListener);
				if (discard) {
					card.getDiscard().setEnabled(true);
					card.getDiscard().addActionListener(moveListener);
				}
			}

	}

	public void deleteListenersToButton(ActionListener moveListener,
			boolean discard) {
		int nComp = getComponentCount();
		CardPanel card;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				card = (CardPanel) getComponent(i);
				card.getUse().setEnabled(false);
				card.getUse().removeActionListener(moveListener);
				if (discard) {
					card.getDiscard().setEnabled(false);
					card.getDiscard().removeActionListener(moveListener);
				}
			}

	}

	public void remove(String cardName) {
		int nComp = getComponentCount();
		for (int i = 0; i < nComp; i++) {
			if (getComponent(i).getName().equals(cardName))
				remove(getComponent(i));
		}
		repaint();
	}

}
