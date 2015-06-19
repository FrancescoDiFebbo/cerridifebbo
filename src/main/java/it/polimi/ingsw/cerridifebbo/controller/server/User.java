package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteUser;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.HumanPlayer;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.Sentence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 */
public class User extends UnicastRemoteObject implements RemoteUser {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	private final transient String name;

	/** The connection. */
	private transient ClientConnection connection;

	/** The player. */
	private transient Player player;

	/** The queue. */
	private transient Queue<Move> queue = new LinkedList<Move>();

	/** The time finished. */
	private transient boolean timeFinished = false;

	/** The reinitialize. */
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
	 *             the remote exception
	 */
	public User(String name, ClientConnection connection) throws RemoteException {
		super();
		this.name = name;
		this.connection = connection;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
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
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Sets the player.
	 *
	 * @param player
	 *            the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Checks if is time finished.
	 *
	 * @return true, if is time finished
	 */
	public boolean isTimeFinished() {
		return timeFinished;
	}

	/**
	 * Sets the time finished.
	 *
	 * @param timeFinished
	 *            the new time finished
	 */
	public void setTimeFinished(boolean timeFinished) {
		this.timeFinished = timeFinished;
	}

	/**
	 * Clear.
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
	 * Check connection.
	 *
	 * @return true, if successful
	 */
	private boolean checkConnection() {
		if (reinitialize) {
			sendGameInformation(currentGame);
			reinitialize = false;
		}
		return isOnline();
	}

	/**
	 * Send message.
	 *
	 * @param message
	 *            the message
	 */
	public void sendMessage(String message) {
		if (checkConnection()) {
			try {
				connection.sendMessage(message);
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Send game information.
	 *
	 * @param game
	 *            the game
	 */
	public void sendGameInformation(Game game) {
		if (isOnline()) {
			try {
				connection.sendGameInformation(game.getMap().getMapRemote(), player.getPlayerRemote(), game.getUsers().size());
				currentGame = game;
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Start turn.
	 */
	public void startTurn() {
		if (checkConnection()) {
			try {
				connection.startTurn();
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	/**
	 * End turn.
	 */
	public void endTurn() {
		if (checkConnection()) {
			try {
				connection.endTurn();
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Gets the move.
	 *
	 * @return the move
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
	 * Ask for move.
	 */
	private void askForMove() {
		try {
			connection.askForMove();
		} catch (RemoteException e) {
			suspend(e);
		}
	}

	/**
	 * Gets the sector.
	 *
	 * @param map
	 *            the map
	 * @return the sector
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
	 * Ask for sector.
	 */
	private void askForSector() {
		try {
			connection.askForSector();
		} catch (RemoteException e) {
			suspend(e);
		}
	}

	/**
	 * Random sector.
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
	 * Gets the card.
	 *
	 * @return the card
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
	 * Ask for card.
	 */
	private void askForCard() {
		try {
			connection.askForCard();
		} catch (RemoteException e) {
			suspend(e);
		}
	}

	/**
	 * Update player.
	 *
	 * @param player
	 *            the player
	 * @param card
	 *            the card
	 * @param added
	 *            the added
	 */
	public void updatePlayer(Player player, Card card, boolean added) {
		if (checkConnection()) {
			try {
				if (card == null) {
					connection.updatePlayer(player.getPlayerRemote(), null, added);
				} else {
					connection.updatePlayer(player.getPlayerRemote(), new ItemCardRemote(card), added);
				}

			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Disconnect.
	 */
	public void disconnect() {
		if (isOnline()) {
			try {
				connection.disconnect();
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	/**
	 * Suspend.
	 *
	 * @param e
	 *            the e
	 */
	public void suspend(Throwable e) {
		connection = null;
		currentGame.informPlayers(player, Sentence.DISCONNECTED, null);
		Application.exception(e, name + " suspended");
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
	 * Put move.
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
