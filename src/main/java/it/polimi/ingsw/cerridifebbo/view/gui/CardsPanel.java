package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import javax.swing.JPanel;

/**
 * This class describes a panel of cardPanel.
 * 
 * @see CardPanel that is a unit of this panel
 * @author cerridifebbo
 *
 */
public class CardsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 165;

	/**
	 * This constructor creates the panel. It takes the player and creates the
	 * cards of the player. The panel use the flow layout.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player
	 * @param playerRace
	 *            the race of the player
	 *
	 */
	public CardsPanel(PlayerRemote player, String playerRace) {
		setOpaque(true);
		setLayout(new FlowLayout());
		if (player.getOwnCards() != null) {
			for (int i = 0; i < player.getOwnCards().size(); i++) {
				add(new CardPanel(player.getOwnCards().get(i).getName(), playerRace));
			}
		}
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphics.BACKGROUND_COLOR);
	}

	/**
	 * This method adds a listener to every card button of the panel.
	 * 
	 * @author cerridifebbo
	 * @param moveListener
	 *            the listener that will be added.
	 * @param discard
	 *            if is true the method adds the listener to the discard button,
	 *            otherwise the method does not add the listener to the discard
	 *            button.
	 */
	public void addListenersToButton(ActionListener moveListener, boolean discard) {
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

	/**
	 * This method deletes a listener to every card button of the panel.
	 * 
	 * @author cerridifebbo
	 * @param moveListener
	 *            the listener that will be deleted.
	 * @param discard
	 *            if is true the method deletes the listener to the discard
	 *            button, otherwise the method does not delete the listener to
	 *            the discard button.
	 */
	public void deleteListenersToButton(ActionListener moveListener, boolean discard) {
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

	/**
	 * This method removes from the panel the card that has the same name of one
	 * of the card of the panel.
	 * 
	 * @author cerridifebbo
	 * @param cardName
	 *            the name of the card that will be removed
	 */
	public void remove(String cardName) {
		int nComp = getComponentCount();
		CardPanel card = null;
		boolean found = false;
		for (int i = 0; !found && i < nComp; i++) {
			if (((CardPanel) getComponent(i)).getName().equals(cardName)) {
				card = (CardPanel) getComponent(i);
				found = true;
			}
		}
		if (found) {
			remove(card);
		}
		repaint();
	}

}
