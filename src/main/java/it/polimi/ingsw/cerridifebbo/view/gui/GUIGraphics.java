package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphics;
import it.polimi.ingsw.cerridifebbo.model.AttackItemCard;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIGraphics extends Graphics implements ActionListener {

	private static final String FRAME_NAME = "ESCAPE FROM THE ALIENS IN OUTER SPACE";
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color FOREGROUND_COLOR = Color.GREEN;
	private MapContainer mapGrid;
	private ServerScrollPane serverMessage;
	private TimerPanel timerPanel;
	private CardsPanel cards;
	private ButtonPanel buttonPanel;
	private JFrame frame;
	private Container contentPane;
	private String move;
	private boolean waitMove;

	public void setMove(String move) {
		this.move = move;
	}

	@Override
	public void initialize(Map map, Player player) {

		frame = new JFrame(FRAME_NAME);
		contentPane = frame.getContentPane();
		contentPane.setBackground(BACKGROUND_COLOR);
		frame.setBackground(BACKGROUND_COLOR);
		mapGrid = new MapContainer(map);
		serverMessage = new ServerScrollPane();
		timerPanel = new TimerPanel();
		cards = new CardsPanel(player);
		buttonPanel = new ButtonPanel();
		contentPane.add(mapGrid);
		contentPane.add(timerPanel);
		contentPane.add(serverMessage);
		contentPane.add(cards);
		contentPane.add(buttonPanel);
		contentPane.setLayout(new MainWindowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	@Override
	public void sendMessage(String message) {
		serverMessage.addText(message);

	}

	public static void main(String args[]) {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		Game game = new Game(null, users);
		game.run();
		GUIGraphics gui = new GUIGraphics();
		game.getUsers().get(0).getPlayer().addCard(new AttackItemCard());
		gui.initialize(game.getMap(), game.getUsers().get(0).getPlayer());

		gui.startTurn();
		System.out.println(gui.declareMove(game.getUsers().get(0).getPlayer()));

	}

	@Override
	public void startTurn() {
		timerPanel.startTimer();
	}

	@Override
	public void endTurn() {
		timerPanel.setVisible(false);
	}

	@Override
	public String declareMove(Player player) {
		ActionListener moveListener = this;
		waitMove = true;
		mapGrid.addListenersToButton(moveListener);
		cards.addListenersToButton(moveListener);
		buttonPanel.addListenersToButton(moveListener);
		
		//mapGrid.deleteListenersToButton(moveListener);
		//cards.deleteListenersToButton(moveListener);
		//buttonPanel.deleteListenersToButton(moveListener);
		return move;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof SectorButton) {
			move = Move.MOVEMENT + " " + e.getActionCommand();
		} else if (e.getSource() instanceof CardButton) {
			move = Move.USEITEMCARD + " " + e.getActionCommand();
		} else if (e.getSource().equals(buttonPanel.getComponent(0))) {
			move = Move.ATTACK;
		} else if (e.getSource().equals(buttonPanel.getComponent(1))) {
			move = Move.FINISH;
		}
		serverMessage.addText(move);
	}

}
