package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * This class describes a panel with buttons.
 * 
 * @author cerridifebbo
 *
 */
public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final String FINISH_TURN = "Finish Turn";
	public static final String ATTACK = "Attack";
	public static final int WIDTH = 400;
	public static final int HEIGHT = 50;

	private JButton finishTurn;
	private JButton attack;
	private String playerRace;

	/**
	 * The constructor of the class creates the panel and the button. It also
	 * sets the layout to gridLayout. The panel has different colors for
	 * different player races.
	 * 
	 * @author cerridifebbo
	 * @param playerRace
	 *            the race of the player
	 */
	public ButtonPanel(String playerRace) {
		this.playerRace = playerRace;
		setButton();
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setForeground(GUIGraphics.getColorRace(playerRace));
		setLayout(new GridLayout());

	}

	/**
	 * This method sets the buttons. If the player is a alien the buttons are
	 * attack and finish, else the button is only finish.
	 * 
	 * @author cerridifebbo
	 * 
	 */
	private void setButton() {
		UIManager.getDefaults().put("Button.disabledText",
				GUIGraphics.getColorRace(playerRace));
		Border thickBorder = new LineBorder(
				GUIGraphics.getColorRace(playerRace), 2);
		finishTurn = new JButton(FINISH_TURN);
		finishTurn.setBackground(GUIGraphics.BACKGROUND_COLOR);
		finishTurn.setForeground(GUIGraphics.getColorRace(playerRace));
		if (GUIGraphics.ALIEN.equals(playerRace)) {
			attack = new JButton(ATTACK);
			attack.setBackground(GUIGraphics.BACKGROUND_COLOR);
			attack.setForeground(GUIGraphics.getColorRace(playerRace));
			add(attack);
			attack.setEnabled(false);
			attack.setBorder(thickBorder);
		}
		add(finishTurn);
		finishTurn.setEnabled(false);
		finishTurn.setBorder(thickBorder);

	}

	/**
	 * This methods adds an action listener to the buttons of the panel.
	 * 
	 * @author cerridifebbo
	 * @param moveListener
	 *            the actionListener that will be added to the buttons
	 */
	public void addListenersToButton(ActionListener moveListener) {
		if (GUIGraphics.ALIEN.equals(playerRace)) {
			attack.setEnabled(true);
			attack.addActionListener(moveListener);
		}
		finishTurn.setEnabled(true);
		finishTurn.addActionListener(moveListener);
	}

	/**
	 * This methods deletes an action listener to the buttons of the panel.
	 * 
	 * @author cerridifebbo
	 * @param moveListener
	 *            the actionListener that will be deleted to the buttons
	 */
	public void deleteListenersToButton(ActionListener moveListener) {
		if (GUIGraphics.ALIEN.equals(playerRace)) {
			attack.setEnabled(false);
			attack.removeActionListener(moveListener);
		}
		finishTurn.setEnabled(false);
		finishTurn.removeActionListener(moveListener);
	}

}
