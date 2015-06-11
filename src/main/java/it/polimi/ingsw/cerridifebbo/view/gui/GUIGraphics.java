package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphics;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.HumanPlayer;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUIGraphics extends Graphics implements ActionListener {

	private static final String FRAME_NAME = "ESCAPE FROM THE ALIENS IN OUTER SPACE";
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color FOREGROUND_COLOR_ALIEN = Color.GREEN;
	public static final Color FOREGROUND_COLOR_HUMAN = Color.BLUE;
	private static final String PLAYER_RACE_HUMAN = "You are a human. Your name is ";
	private static final String PLAYER_RACE_ALIEN = "You are an alien. Your name is ";
	private MapContainer mapGrid;
	private ServerScrollPane serverMessage;
	private TimerPanel timerPanel;
	private CardsPanel cards;
	private ButtonPanel buttonPanel;
	private JFrame frame;
	private Container contentPane;
	private ActionListener moveListener;
	private boolean declareSector;
	private String playerRace;
	public static final String HUMAN = "HUMAN";
	public static final String ALIEN = "ALIEN";
	private int turn = 0;
	public static final int WIDTH = 1425;
	public static final int HEIGHT = 810;
	private static final int SPACE_BETWEEN_COMP_Y2 = 20;
	private static final int SPACE_BETWEEN_COMP_Y1 = 80;

	@Override
	public void initialize(Map map, Player player, int numberOfPlayers) {
		if (player instanceof HumanPlayer) {
			playerRace = HUMAN;
		} else {
			playerRace = ALIEN;
		}
		frame = new JFrame(FRAME_NAME);
		contentPane = frame.getContentPane();
		contentPane.setBackground(BACKGROUND_COLOR);
		frame.setBackground(BACKGROUND_COLOR);
		mapGrid = new MapContainer(map);
		mapGrid.setPlayerPawn(player);
		serverMessage = new ServerScrollPane(playerRace);
		timerPanel = new TimerPanel(playerRace);
		cards = new CardsPanel(player, playerRace);
		buttonPanel = new ButtonPanel(playerRace);
		contentPane.add(mapGrid);
		contentPane.add(timerPanel);
		contentPane.add(serverMessage);
		contentPane.add(cards);
		contentPane.add(buttonPanel);
		contentPane.setLayout(new CustomLayout(SPACE_BETWEEN_COMP_Y1,
				SPACE_BETWEEN_COMP_Y2));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setVisible(true);
		moveListener = this;
		initialized = true;
		String message;
		if (playerRace.equals(HUMAN)) {
			message = PLAYER_RACE_HUMAN;
		} else {
			message = PLAYER_RACE_ALIEN;
		}
		serverMessage.addText(message
				+ player.getPlayerCard().getCharacterName());
		if (numberOfPlayers == 2) {
			serverMessage
					.addText("You are not alone. There is a creature on the ship");
		} else {
			serverMessage.addText("You are not alone. There are "
					+ (numberOfPlayers - 1) + " creatures on the ship");
		}
	}

	public static Color getColorRace(String race) {
		if (race.equals(HUMAN)) {
			return FOREGROUND_COLOR_HUMAN;
		}
		return FOREGROUND_COLOR_ALIEN;
	}

	@Override
	public void sendMessage(String message) {
		serverMessage.addText(message);
		serverMessage.repaint();
	}

	@Override
	public void startTurn() {
		sendMessage("TURN: " + ++turn);
		sendMessage("It's your turn");
		timerPanel.startTimer();
		timerPanel.repaint();
	}

	@Override
	public void endTurn() {
		timerPanel.stopTimer();
		sendMessage("------------------------------");
	}

	@Override
	public void declareMove() {
		sendMessage("Make your move");
		addListeners(moveListener, false);
	}

	private void addListeners(ActionListener moveListener, boolean discard) {
		mapGrid.addListenersToButton(moveListener);
		cards.addListenersToButton(moveListener, discard);
		buttonPanel.addListenersToButton(moveListener);
	}

	private void deleteListeners(ActionListener moveListener, boolean discard) {
		mapGrid.deleteListenersToButton(moveListener);
		cards.deleteListenersToButton(moveListener, discard);
		buttonPanel.deleteListenersToButton(moveListener);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof SectorButton) {
			if (declareSector) {
				getNetworkInterface().sendToServer(Move.SECTOR,
						e.getActionCommand());
				mapGrid.deleteListenersToButton(moveListener);
				declareSector = false;
			} else {
				getNetworkInterface().sendToServer(Move.MOVEMENT,
						e.getActionCommand());
				deleteListeners(moveListener, false);
			}
		} else if (e.getActionCommand().equals(CardPanel.USE_TEXT)) {
			String cardName = ((JButton) e.getSource()).getName();
			getNetworkInterface().sendToServer(Move.USEITEMCARD, cardName);
			deleteListeners(moveListener, false);
		} else if (e.getActionCommand().equals(CardPanel.DISCARD_TEXT)) {
			String cardName = ((JButton) e.getSource()).getName();
			getNetworkInterface().sendToServer(Move.DELETECARD, cardName);
			cards.deleteListenersToButton(moveListener, true);
		} else if (e.getActionCommand().equals(ButtonPanel.ATTACK)) {

			getNetworkInterface().sendToServer(Move.ATTACK, null);
			deleteListeners(moveListener, false);
		} else if (e.getActionCommand().equals(ButtonPanel.FINISH_TURN)) {
			getNetworkInterface().sendToServer(Move.FINISH, null);
			deleteListeners(moveListener, false);
		}
	}

	@Override
	public void declareSector() {
		sendMessage("Choose a sector");
		declareSector = true;
		mapGrid.addListenersToButton(moveListener);
	}

	@Override
	public void updatePlayerPosition(Player player) {
		mapGrid.setPlayerPawn(player);
	}

	@Override
	public void declareCard() {
		cards.addListenersToButton(moveListener, true);
	}

	@Override
	public void deletePlayerCard(Player player, Card card) {
		cards.remove(card.toString());
		cards.repaint();
	}

	@Override
	public void addPlayerCard(Player player, Card card) {
		cards.add(new CardPanel(card.toString(), playerRace));
	}

	@Override
	public void updateEscapeHatch(Map map, Sector sector) {
		mapGrid.updateEscapeHatchStatus(sector.toString());
	}

}
