package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;

import it.polimi.ingsw.cerridifebbo.model.User;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel {

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
		setBackground(MainWindow.BACKGROUND_COLOR);
		setForeground(MainWindow.FOREGROUND_COLOR);

	}

	private void setButton() {
		finishTurn = new JButton(FINISH_TURN);
		finishTurn.setBackground(MainWindow.BACKGROUND_COLOR);
		finishTurn.setForeground(MainWindow.FOREGROUND_COLOR);
		movement = new JButton(MOVEMENT);
		movement.setBackground(MainWindow.BACKGROUND_COLOR);
		movement.setForeground(MainWindow.FOREGROUND_COLOR);
		useCard = new JButton(USE_CARD);
		useCard.setBackground(MainWindow.BACKGROUND_COLOR);
		useCard.setForeground(MainWindow.FOREGROUND_COLOR);
		deleteCard = new JButton(DELETE_CARD);
		deleteCard.setBackground(MainWindow.BACKGROUND_COLOR);
		deleteCard.setForeground(MainWindow.FOREGROUND_COLOR);
		attack = new JButton(ATTACK);
		attack.setBackground(MainWindow.BACKGROUND_COLOR);
		attack.setForeground(MainWindow.FOREGROUND_COLOR);
		add(useCard);
		add(deleteCard);
		add(movement);
		add(attack);
		add(finishTurn);

	}
}
