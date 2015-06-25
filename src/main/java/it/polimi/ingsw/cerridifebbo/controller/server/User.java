package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Util;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteUser;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.HumanPlayer;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.Sentence;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * The Class User. It represents the client on server. It provides methods to
 * contact client from game.
 *
 * @author cerridifebbo
 */
public class User extends UnicastRemoteObject implements RemoteUser {

	private static final long serialVersionUID = 1L;

	/** The username of the client. */
	private final transient String name;

	/** The connection established with client. */
	private transient ClientConnection connection;

	/** The player in the game. */
	private transient Player player;

	/** The queue for incoming moves. */
	private transient Queue<Move> queue = new LinkedList<Move>();

	/** Indicates if user has finished his turn time */
	private transient boolean timeFinished = false;

	/**
	 * Indicates if the user has to be reinitialized after a suspension on
	 * connection.
	 */
	private transient boolean reinitialize = false;

	/** The current game. */
	private transient Game currentGame;

	/**
	 * Instantiates a new user.
	 *
	 * @param name
	 *            the name
	 * @param connection
	 *            the connection
	 * @throws RemoteException
	 *             if I/O errors occurs
	 */
	public User(String name, ClientConnection connection) throws RemoteException {
		super();
		this.name = name;
		this.connection = connection;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the connection.
	 *
	 * @param connection
	 *            the new connection
	 */
	public void setConnection(ClientConnection connection) {
		this.connection = connection;
		reinitialize = true;
	}

	/**
	 * Checks if is online.
	 *
	 * @return true, if is online
	 */
	public boolean isOnline() {
		return connection != null;
	}

	/**
	 * Gets the player in current game.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player of the user.
	 *
	 * @param player
	 *            the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Checks if time is finished.
	 *
	 * @return true, if is time finished
	 */
	public boolean isTimeFinished() {
		return timeFinished;
	}

	/**
	 * Sets the time if it's finished.
	 *
	 * @param timeFinished
	 *            the new time finished
	 */
	public void setTimeFinished(boolean timeFinished) {
		this.timeFinished = timeFinished;
	}

	/**
	 * Clears the queue of moves and clears the stats of the player.
	 */
	public void clear() {
		synchronized (queue) {
			queue.clear();
			timeFinished = false;
			if (player instanceof HumanPlayer) {
				((HumanPlayer) player).clear();
			}
		}
	}

	/**
	 * Check the connection to the client.
	 *
	 * @return true, if successful
	 */
	private boolean checkConnection() {
		if (reinitialize) {
			sendGameInformation(currentGame);
			reinitialize = false;
			currentGame.informPlayers(player, Sentence.CONNECTED, null);
		}
		return isOnline();
	}

	/**
	 * Sends message to the client.
	 *
	 * @param message
	 *            the message
	 */
	public void sendMessage(String message) {
		if (checkConnection()) {
			try {
				connection.sendMessage(message);
			} catch (IOException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Sends game information to client.
	 *
	 * @param game
	 *            the game
	 */
	public void sendGameInformation(Game game) {
		if (isOnline()) {
			try {
				connection.sendGameInformation(game.getMap().getMapRemote(), player.getPlayerRemote(), game.getUsers().size());
				currentGame = game;
			} catch (IOException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Signals the start turn to client.
	 */
	public void startTurn() {
		if (checkConnection()) {
			try {
				connection.startTurn();
			} catch (IOException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Signals the end turn to client.
	 */
	public void endTurn() {
		if (checkConnection()) {
			try {
				connection.endTurn();
			} catch (IOException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Gets the move from client.
	 *
	 * @return the move, if there is no connection or turn time is finished
	 *         returns a TIMEFINISHED move.
	 */
	public Move getMove() {
		if (checkConnection()) {
			askForMove();
			Move move = null;
			do {
				synchronized (queue) {
					move = queue.poll();
				}
			} while (!timeFinished && move == null);
			if (timeFinished) {
				return new Move(Move.TIMEFINISHED, null);
			}
			return move;
		} else {
			return new Move(Move.TIMEFINISHED, null);
		}
	}

	/**
	 * Asks for move from client.
	 */
	private void askForMove() {
		try {
			connection.askForMove();
		} catch (IOException e) {
			suspend(e);
		}
	}

	/**
	 * Gets the sector from client.
	 *
	 * @param map
	 *            the map
	 * @return the sector, if there is no connection or turn time is finished
	 *         returns a random sector.
	 */
	public Sector getSector(Map map) {
		if (checkConnection()) {
			askForSector();
			Move move = null;
			do {
				synchronized (queue) {
					move = queue.poll();
				}
			} while (!timeFinished && move == null);
			if (timeFinished) {
				return randomSector(map);
			}
			return map.getCell(move.getTarget());
		} else {
			return randomSector(map);
		}
	}

	/**
	 * Asks for sector from client.
	 */
	private void askForSector() {
		try {
			connection.askForSector();
		} catch (IOException e) {
			suspend(e);
		}
	}

	/**
	 * It returns a random sector from the map.
	 *
	 * @param map
	 *            the map
	 * @return the sector
	 */
	private Sector randomSector(Map map) {
		Random random = new Random();
		Sector sector = null;
		do {
			sector = map.getCell(random.nextInt(Map.ROWMAP), random.nextInt(Map.COLUMNMAP));
		} while (sector == null);
		return sector;
	}

	/**
	 * Gets the card from client.
	 *
	 * @return the card, if there is no connection or turn time is finished
	 *         returns the first card of player's deck.
	 */
	public Move getCard() {
		if (checkConnection()) {
			askForCard();
			Move move = null;
			do {
				synchronized (queue) {
					move = queue.poll();
				}
			} while (!timeFinished && move == null);
			if (timeFinished) {
				return new Move(Move.DELETECARD, player.getOwnCards().get(0).toString());
			}
			return move;
		} else {
			return new Move(Move.DELETECARD, player.getOwnCards().get(0).toString());
		}

	}

	/**
	 * Ask for card from client.
	 */
	private void askForCard() {
		try {
			connection.askForCard();
		} catch (IOException e) {
			suspend(e);
		}
	}

	/**
	 * Sends player's update to client.
	 *
	 * @param player
	 *            the player
	 * @param card
	 *            the card
	 * @param added
	 *            indicates if card has been added or removed from the deck
	 */
	public void updatePlayer(Player player, Card card, boolean added) {
		if (checkConnection()) {
			try {
				if (card == null) {
					connection.sendPlayerUpdate(player.getPlayerRemote(), null, added);
				} else {
					connection.sendPlayerUpdate(player.getPlayerRemote(), new ItemCardRemote(card), added);
				}

			} catch (IOException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Sends hatch update to client.
	 *
	 * @param sector
	 *            the sector
	 */
	public void updateHatch(Sector sector) {
		if (checkConnection()) {
			try {
				connection.sendHatchUpdate(currentGame.getMap().getMapRemote(), new SectorRemote(sector));
			} catch (IOException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Disconnects the client from server.
	 */
	public void disconnect() {
		if (isOnline()) {
			try {
				connection.disconnect();
			} catch (IOException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Suspend the user. It removes the client connection.
	 *
	 * @param e
	 *            the exception
	 */
	public void suspend(Throwable e) {
		connection = null;
		timeFinished = true;
		currentGame.informPlayers(player, Sentence.DISCONNECTED, null);
		Util.exception(e, name + " suspended");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.RemoteUser#sendMove(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	public void sendMove(String action, String target) throws RemoteException {
		putMove(action, target);
	}

	/**
	 * Put a incoming move in the queue.
	 *
	 * @param action
	 *            the action
	 * @param target
	 *            the target
	 */
	public void putMove(String action, String target) {
		synchronized (queue) {
			queue.offer(new Move(action, target));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.rmi.server.RemoteObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.rmi.server.RemoteObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
