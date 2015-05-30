package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphics;
import it.polimi.ingsw.cerridifebbo.model.AttackItemCard;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.SedativesItemCard;
import it.polimi.ingsw.cerridifebbo.model.SpotlightItemCard;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.swing.JFrame;

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
	private ActionListener moveListener;
	private boolean declareSector;
	private boolean declareCard;
	private Player player;

	@Override
	public void initialize(Map map, Player player) {

		this.player = player;
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
		moveListener = this;

	}

	@Override
	public void sendMessage(String message) {
		serverMessage.addText(message);

	}

	public static void main(String args[]) {
		List<User> users = new ArrayList<User>();
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		Game game = new Game(null, users);
		game.run();
		GUIGraphics gui = new GUIGraphics();
		game.getUsers().get(0).getPlayer().addCard(new AttackItemCard());
		gui.initialize(game.getMap(), game.getUsers().get(0).getPlayer());
		gui.startTurn();
		game.getUsers().get(0).getPlayer().addCard(new SedativesItemCard());
		gui.deletePlayerCard(game.getUsers().get(0).getPlayer(), game
				.getUsers().get(0).getPlayer().getOwnCards().get(0));
		gui.addPlayerCard(game.getUsers().get(0).getPlayer(),
				new SpotlightItemCard());

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
	public void declareMove() {
		addListeners(moveListener);
	}

	private void addListeners(ActionListener moveListener) {
		mapGrid.addListenersToButton(moveListener);
		cards.addListenersToButton(moveListener);
		buttonPanel.addListenersToButton(moveListener);
	}

	private void deleteListeners(ActionListener moveListener) {
		mapGrid.deleteListenersToButton(moveListener);
		cards.deleteListenersToButton(moveListener);
		buttonPanel.deleteListenersToButton(moveListener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof SectorButton) {
			if (declareSector) {
				move = e.getActionCommand();
				// getClient().sendToServer(move);
				mapGrid.deleteListenersToButton(moveListener);
				declareSector = false;
			} else {
				move = Move.MOVEMENT + " " + e.getActionCommand();
				// getClient().sendToServer(move);
				deleteListeners(moveListener);
			}
		} else if (e.getSource() instanceof CardButton) {
			if (declareCard) {
				move = e.getActionCommand();
				// getClient().sendToServer(move);
				cards.deleteListenersToButton(moveListener);
				declareCard = false;
			} else {
				move = Move.USEITEMCARD + " " + e.getActionCommand();
				// getClient().sendToServer(move);
				deleteListeners(moveListener);
			}
		} else if (e.getSource().equals(buttonPanel.getComponent(0))) {
			// getClient().sendToServer(move);
			move = Move.ATTACK + " " + player.getPosition();
			deleteListeners(moveListener);
		} else if (e.getSource().equals(buttonPanel.getComponent(1))) {
			// getClient().sendToServer(move);
			move = Move.FINISH;
			deleteListeners(moveListener);
		}
		serverMessage.addText(move);
	}

	@Override
	public void declareSector() {
		declareSector = true;
		mapGrid.addListenersToButton(moveListener);
	}

	@Override
	public void updatePlayerPosition(Player player) {
		this.player = player;
	}

	@Override
	public void declareCard() {
		declareCard = true;
		cards.addListenersToButton(moveListener);
	}

	@Override
	public void deletePlayerCard(Player player, Card card) {
		this.player = player;
		cards.remove(card.toString());
		cards.repaint();
	}

	@Override
	public void addPlayerCard(Player player, Card card) {
		this.player = player;
		cards.add(new CardButton(card.toString()));

	}

}
