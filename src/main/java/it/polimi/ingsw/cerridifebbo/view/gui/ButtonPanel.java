package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final String FINISH_TURN = "Finish Turn";
	public static final String ATTACK = "Attack";
	public static final int WIDTH = 400;
	public static final int HEIGHT = 50;

	private JButton finishTurn;
	private JButton attack;
	private String playerRace;

	public ButtonPanel(String playerRace) {
		this.playerRace = playerRace;
		setButton();
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setForeground(GUIGraphics.getColorRace(playerRace));
		setLayout(new GridLayout());

	}

	private void setButton() {
		Border thickBorder = new LineBorder(
				GUIGraphics.getColorRace(playerRace), 2);
		finishTurn = new JButton(FINISH_TURN);
		finishTurn.setBackground(GUIGraphics.BACKGROUND_COLOR);
		finishTurn.setForeground(GUIGraphics.getColorRace(playerRace));
		attack = new JButton(ATTACK);
		attack.setBackground(GUIGraphics.BACKGROUND_COLOR);
		attack.setForeground(GUIGraphics.getColorRace(playerRace));
		add(attack);
		attack.setBorder(thickBorder);
		add(finishTurn);
		finishTurn.setBorder(thickBorder);

	}

	public void addListenersToButton(ActionListener moveListener) {

		attack.addActionListener(moveListener);
		finishTurn.addActionListener(moveListener);
	}

	public void deleteListenersToButton(ActionListener moveListener) {
		attack.removeActionListener(moveListener);
		finishTurn.removeActionListener(moveListener);
	}

}
