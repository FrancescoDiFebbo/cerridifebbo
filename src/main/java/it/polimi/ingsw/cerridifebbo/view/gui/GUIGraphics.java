package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphics;
import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;
import it.polimi.ingsw.cerridifebbo.model.AlienPlayer;
import it.polimi.ingsw.cerridifebbo.model.Move;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * This class describes a GUI Graphics.
 * 
 * @see Graphics that is implemented by this classs
 * @author cerridifebbo
 *
 */
public class GUIGraphics extends Graphics implements ActionListener {

	private static final String FRAME_NAME = "ESCAPE FROM THE ALIENS IN OUTER SPACE";
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color FOREGROUND_COLOR_ALIEN = Color.GREEN;
	public static final Color FOREGROUND_COLOR_HUMAN = Color.BLUE;
	private static final String PLAYER_RACE_HUMAN = "You are a human. Your name is ";
	private static final String PLAYER_RACE_ALIEN = "You are an alien. Your name is ";
	private static final String SECTOR_ESCAPE_UPDATE = " is not more available for escape";
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
	private static final String ALIEN_CLASS = AlienPlayer.class.getSimpleName();
	private boolean listenerOn = false;
	private boolean declareCard = false;

	/**
	 * This method initializes the graphics.It creates the main window and adds
	 * the mapGrid, the serverMessage and the buttonPanel. It also shows in the
	 * serverMessage a brief message.
	 * 
	 * @author cerridifebbo
	 * @param map
	 *            the map of the specific game
	 * @param player
	 *            the player used by the client
	 * @param numberOfPlayers
	 *            the number of player of the specific game
	 */
	@Override
	public void initialize(MapRemote map, PlayerRemote player, int numberOfPlayers) {
		if (player.getRace().equals(ALIEN_CLASS)) {
			playerRace = ALIEN;
		} else {
			playerRace = HUMAN;
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
		contentPane.setLayout(new CustomLayout(SPACE_BETWEEN_COMP_Y1, SPACE_BETWEEN_COMP_Y2));
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
		serverMessage.addText(message + player.getPlayerCard().getName());
		if (numberOfPlayers == 2) {
			serverMessage.addText("You are not alone. There is a creature on the ship");
		} else {
			serverMessage.addText("You are not alone. There are " + (numberOfPlayers - 1) + " creatures on the ship");
		}
	}

	/**
	 * This method return a color that depends on the race of the player.
	 * 
	 * @author cerridifebbo
	 * @param race
	 *            the race of the current player
	 * @return the foreground color. It could be FREGROUND_COLOR_HUMAN or
	 *         FOREGROUND_COLOR_ALIEN
	 */
	public static Color getColorRace(String race) {
		if (race.equals(HUMAN)) {
			return FOREGROUND_COLOR_HUMAN;
		}
		return FOREGROUND_COLOR_ALIEN;
	}

	/**
	 * This method shows in the serverMessag the message parameter.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void sendMessage(String message) {
		serverMessage.addText(message);
		serverMessage.repaint();
	}

	/**
	 * This method prepares all the staff for start the player's turn. It also
	 * run a timer that indicates the maximum player's time turn. It also shows
	 * a start turn message..
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void startTurn() {
		sendMessage("TURN: " + ++turn);
		sendMessage("It's your turn");
		timerPanel.startTimer();
		timerPanel.repaint();
	}

	/**
	 * This method prepares all the staff for end the player's turn.It stops the
	 * timer.It also prints a end turn message.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void endTurn() {
		timerPanel.stopTimer();
		sendMessage("------------------------------");
		if (listenerOn) {
			if (declareSector) {
				listenerOn = false;
				declareSector = false;
				mapGrid.deleteListenersToButton(moveListener);
			}
			if (declareCard) {
				listenerOn = false;
				declareCard = false;
				cards.deleteListenersToButton(moveListener, true);
			}
			deleteListeners(moveListener, false);
		}
	}

	/**
	 * This method prepares all the staff for prepare the player to declare a
	 * move.It adds an action listener to mapGrid, cards and buttonPanel. It
	 * also shows a message.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void declareMove() {
		sendMessage("Make your move");
		addListeners(moveListener, false);
		listenerOn = true;
	}

	/**
	 * This methods adds an action listener to mapGrid, cards and buttonPanel.
	 * 
	 * @author cerridifebbo
	 */
	private void addListeners(ActionListener moveListener, boolean discard) {
		mapGrid.addListenersToButton(moveListener);
		cards.addListenersToButton(moveListener, discard);
		buttonPanel.addListenersToButton(moveListener);
	}

	/**
	 * This methods deletes an action listener to mapGrid, cards and
	 * buttonPanel.
	 * 
	 * @author cerridifebbo
	 */
	private void deleteListeners(ActionListener moveListener, boolean discard) {
		listenerOn = false;
		mapGrid.deleteListenersToButton(moveListener);
		cards.deleteListenersToButton(moveListener, discard);
		buttonPanel.deleteListenersToButton(moveListener);
	}

	/**
	 * This methods perform the action of the actionListener. It decides who
	 * invokes this method and then the methods send to the network the move of
	 * the player.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof SectorButton) {
			if (declareSector) {
				listenerOn = false;
				declareSector = false;
				getNetworkInterface().sendToServer(Move.SECTOR, e.getActionCommand());
				mapGrid.deleteListenersToButton(moveListener);
			} else {
				getNetworkInterface().sendToServer(Move.MOVEMENT, e.getActionCommand());
				deleteListeners(moveListener, false);
			}
		} else if (e.getActionCommand().equals(CardPanel.USE_TEXT)) {
			String cardName = ((JButton) e.getSource()).getName();
			getNetworkInterface().sendToServer(Move.USEITEMCARD, cardName);
			deleteListeners(moveListener, false);
		} else if (e.getActionCommand().equals(CardPanel.DISCARD_TEXT)) {
			listenerOn = false;
			declareCard = false;
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

	/**
	 * This method prepares all the staff for prepare the player to declare a
	 * sector.It adds an action listener to mapGrid. It also shows a message.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void declareSector() {
		sendMessage("Choose a sector");
		declareSector = true;
		listenerOn = true;
		mapGrid.addListenersToButton(moveListener);
	}

	/**
	 * This method update the position of the player in the map.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just changed his position.
	 */
	@Override
	public void updatePlayerPosition(PlayerRemote player) {
		mapGrid.setPlayerPawn(player);
	}

	/**
	 * This method prepares all the staff for prepare the player to declare a
	 * card.It adds an action listener to cards.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void declareCard() {
		serverMessage.addText("You reach the maximum number of cards");
		serverMessage.addText("use or discard one of your cards");
		listenerOn = true;
		declareCard = true;
		cards.addListenersToButton(moveListener, true);
	}

	/**
	 * This method update the cards of the player.It removes the card from cards
	 * and repaints it.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just deleted a card
	 * @param card
	 *            the card that has just been deleted
	 */
	@Override
	public void deletePlayerCard(PlayerRemote player, ItemCardRemote card) {
		cards.remove(card.getName());
		cards.repaint();
	}

	/**
	 * This method update the cards of the player.It adds the card from cards.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just added a card
	 * @param card
	 *            the card that has just been added
	 */
	@Override
	public void addPlayerCard(PlayerRemote player, ItemCardRemote card) {
		cards.add(new CardPanel(card.getName(), playerRace));
	}

	/**
	 * This method update the status of the parameter sector of the parameter
	 * map.
	 * 
	 * @author cerridifebbo
	 * @param map
	 *            the current map of the game
	 * @param sector
	 *            the sector that has just been modified
	 */
	@Override
	public void updateEscapeHatch(MapRemote map, SectorRemote sector) {
		serverMessage.addText(sector.getCoordinate() + " " + SECTOR_ESCAPE_UPDATE);
		mapGrid.updateEscapeHatchStatus(sector.getCoordinate());
	}

}
