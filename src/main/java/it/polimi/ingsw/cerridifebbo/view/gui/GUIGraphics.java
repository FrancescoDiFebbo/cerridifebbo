package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphics;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.SedativesItemCard;
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

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIGraphics extends Graphics {

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
		users.get(0).getPlayer().addCard(new SedativesItemCard());
		GUIGraphics gui = new GUIGraphics();
		gui.initialize(game.getMap(), game.getUsers().get(0).getPlayer());
		gui.startTurn();

	}

	@Override
	public void startTurn() {
		timerPanel.startTimer();
		mapGrid.addListenersToButton();
		cards.addListenersToButton();
		buttonPanel.addListenersToButton();
		
	}

	@Override
	public void endTurn() {
		timerPanel.setVisible(false);
		mapGrid.deleteListenersToButton();
		cards.deleteListenersToButton();
		buttonPanel.deleteListenersToButton();
	}

	@Override
	public String declareMove(Player player) {
		move = null;
		mapGrid.addListenersToButton();
		cards.addListenersToButton();
		buttonPanel.addListenersToButton();
		while (move != null) {

		}
		return move;
	}

}
