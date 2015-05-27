package it.polimi.ingsw.cerridifebbo.view.gui;

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
	public static final int HEIGHT = 600;

	private JButton finishTurn;
	private JButton movement;
	private JButton useCard;
	private JButton deleteCard;
	private JButton attack;

	public ButtonPanel() {
		setButton();
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphic.BACKGROUND_COLOR);
		setForeground(GUIGraphic.FOREGROUND_COLOR);

	}

	private void setButton() {
		finishTurn = new JButton(FINISH_TURN);
		finishTurn.setBackground(GUIGraphic.BACKGROUND_COLOR);
		finishTurn.setForeground(GUIGraphic.FOREGROUND_COLOR);
		movement = new JButton(MOVEMENT);
		movement.setBackground(GUIGraphic.BACKGROUND_COLOR);
		movement.setForeground(GUIGraphic.FOREGROUND_COLOR);
		useCard = new JButton(USE_CARD);
		useCard.setBackground(GUIGraphic.BACKGROUND_COLOR);
		useCard.setForeground(GUIGraphic.FOREGROUND_COLOR);
		deleteCard = new JButton(DELETE_CARD);
		deleteCard.setBackground(GUIGraphic.BACKGROUND_COLOR);
		deleteCard.setForeground(GUIGraphic.FOREGROUND_COLOR);
		attack = new JButton(ATTACK);
		attack.setBackground(GUIGraphic.BACKGROUND_COLOR);
		attack.setForeground(GUIGraphic.FOREGROUND_COLOR);
		add(useCard);
		add(deleteCard);
		add(movement);
		add(attack);
		add(finishTurn);

	}
}
