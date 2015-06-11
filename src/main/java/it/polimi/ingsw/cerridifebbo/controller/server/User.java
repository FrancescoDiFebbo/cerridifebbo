package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteUser;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Game.Sentence;
import it.polimi.ingsw.cerridifebbo.model.HumanPlayer;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class User extends UnicastRemoteObject implements RemoteUser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final transient String name;
	private transient ClientConnection connection;
	private transient Player player;
	private transient Queue<Move> queue = new LinkedList<Move>();
	private transient boolean timeFinished = false;
	private transient boolean reinitialize = false;
	private transient Game currentGame;

	public User(String name, ClientConnection connection) throws RemoteException {
		super();
		this.name = name;
		this.connection = connection;
	}

	public String getName() {
		return name;
	}

	public void setConnection(ClientConnection connection) {
		this.connection = connection;
		reinitialize = true;
	}

	public boolean isOnline() {
		return connection != null;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public boolean isTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(boolean timeFinished) {
		this.timeFinished = timeFinished;
	}

	public void clear() {
		synchronized (queue) {
			queue.clear();
			timeFinished = false;
			if (player instanceof HumanPlayer) {
				((HumanPlayer) player).clear();
			}
		}
	}
	
	private boolean checkConnection(){
		if (reinitialize) {
			sendGameInformation(currentGame);
			reinitialize = false;
		}
		return isOnline();
	}

	public void sendMessage(String message) {
		if (checkConnection()) {
			try {
				connection.sendMessage(message);
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	public void sendGameInformation(Game game) {
		if (isOnline()) {
			try {
				connection.sendGameInformation(game.getMap(), player, game.getUsers().size());
				currentGame = game;
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	public void startTurn() {
		if (checkConnection()) {
			try {
				connection.startTurn();
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	public void endTurn() {
		if (checkConnection()) {
			try {
				connection.endTurn();
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

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

	private void askForMove() {
		try {
			connection.askForMove();
		} catch (RemoteException e) {
			suspend(e);
		}
	}

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

	private void askForSector() {
		try {
			connection.askForSector();
		} catch (RemoteException e) {
			suspend(e);
		}
	}

	private Sector randomSector(Map map) {
		Random random = new Random();
		Sector sector = null;
		do {
			sector = map.getCell(random.nextInt(Map.ROWMAP), random.nextInt(Map.COLUMNMAP));
		} while (sector == null);
		return sector;
	}

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

	private void askForCard() {
		try {
			connection.askForCard();
		} catch (RemoteException e) {
			suspend(e);
		}
	}

	public void updatePlayer(Player player, Card card, boolean added) {
		if (checkConnection()) {
			try {
				connection.updatePlayer(player, card, added);
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	public void disconnect() {
		if (isOnline()) {
			try {
				connection.disconnect();
			} catch (RemoteException e) {
				suspend(e);
			}
		}
	}

	public boolean poke() {
		if (isOnline()) {
			try {
				return connection.poke();
			} catch (RemoteException e) {
				suspend(e);
			}
		}
		return false;
	}

	public void suspend(Throwable e) {
		connection = null;
		currentGame.informPlayers(player, Sentence.DISCONNECTED, null);
		Application.exception(e, name + " suspended", true);
	}

	@Override
	public void sendMove(String action, String target) throws RemoteException {
		putMove(action, target);
	}

	public void putMove(String action, String target) {
		synchronized (queue) {
			queue.offer(new Move(action, target));
		}
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
