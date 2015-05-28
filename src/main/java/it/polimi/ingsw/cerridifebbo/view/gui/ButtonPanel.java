package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class ButtonPanel extends JPanel implements ActionListener {

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
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setForeground(GUIGraphics.FOREGROUND_COLOR);

	}

	private void setButton() {
		finishTurn = new JButton(FINISH_TURN);
		finishTurn.setBackground(GUIGraphics.BACKGROUND_COLOR);
		finishTurn.setForeground(GUIGraphics.FOREGROUND_COLOR);
		movement = new JButton(MOVEMENT);
		movement.setBackground(GUIGraphics.BACKGROUND_COLOR);
		movement.setForeground(GUIGraphics.FOREGROUND_COLOR);
		useCard = new JButton(USE_CARD);
		useCard.setBackground(GUIGraphics.BACKGROUND_COLOR);
		useCard.setForeground(GUIGraphics.FOREGROUND_COLOR);
		deleteCard = new JButton(DELETE_CARD);
		deleteCard.setBackground(GUIGraphics.BACKGROUND_COLOR);
		deleteCard.setForeground(GUIGraphics.FOREGROUND_COLOR);
		attack = new JButton(ATTACK);
		attack.setBackground(GUIGraphics.BACKGROUND_COLOR);
		attack.setForeground(GUIGraphics.FOREGROUND_COLOR);
		add(useCard);
		add(deleteCard);
		add(movement);
		add(attack);
		add(finishTurn);

	}

	public void addListenersToButton() {
		useCard.setEnabled(true);
		deleteCard.setEnabled(true);
		movement.setEnabled(true);
		attack.setEnabled(true);
		finishTurn.setEnabled(true);
	}

	public void deleteListenersToButton() {
		UIManager.getDefaults().put("Button.disabledText",
				GUIGraphics.FOREGROUND_COLOR);
		useCard.setEnabled(false);
		deleteCard.setEnabled(false);
		movement.setEnabled(false);
		attack.setEnabled(false);
		finishTurn.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		e.getActionCommand();
		
	}
}
