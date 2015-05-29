package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final String FINISH_TURN = "Finish Turn";
	public static final String MOVEMENT = "Movement";
	public static final String USE_CARD = "Use Card";
	public static final String DELETE_CARD = "Delete Card";
	public static final String ATTACK = "Attack";
	public static final int WIDTH = 400;
	public static final int HEIGHT = 50;

	private JButton finishTurn;
	private JButton attack;

	public ButtonPanel() {
		setButton();
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setForeground(GUIGraphics.FOREGROUND_COLOR);
		setLayout(new GridLayout());

	}

	private void setButton() {
		finishTurn = new JButton(FINISH_TURN);
		finishTurn.setBackground(GUIGraphics.BACKGROUND_COLOR);
		finishTurn.setForeground(GUIGraphics.FOREGROUND_COLOR);
		attack = new JButton(ATTACK);
		attack.setBackground(GUIGraphics.BACKGROUND_COLOR);
		attack.setForeground(GUIGraphics.FOREGROUND_COLOR);
		add(attack);
		add(finishTurn);

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
