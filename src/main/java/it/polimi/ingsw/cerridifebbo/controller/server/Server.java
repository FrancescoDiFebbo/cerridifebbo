package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.model.Game;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Class Server.
 * 
 * @author cerridifebbo
 */
public class Server {

	/**
	 * It indicates the interval in milliseconds that separate the start of the
	 * game from the last player connected
	 */
	private static final int TIMEOUT_NEWGAME = 10000;

	/** The instance of Server. */
	private static Server instance = new Server();

	/** The server connection. */
	private ServerConnection rmi, socket;

	/** The users connected to the server. */
	private List<User> users = new ArrayList<User>();

	/** The room where users wait to be included in a game. */
	private List<User> room = new ArrayList<User>();

	/** The running games on server. */
	private Map<Game, Thread> games = new HashMap<Game, Thread>();

	/**
	 * The timer that separate the start of the game from the last player
	 * connected
	 */
	private Timer timeout = new Timer();

	/** Indicates if server is started */
	private boolean started = false;

	/**
	 * Instantiates a new server.
	 */
	private Server() {

	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Server.getInstance().start();
	}

	/**
	 * Gets the single instance of Server.
	 *
	 * @return single instance of Server
	 */
	public static Server getInstance() {
		return instance;
	}

	/**
	 * This method start the server. It listens for incoming rmi and socket
	 * connections.
	 */
	private void start() {
		rmi = ServerConnectionFactory.getConnection(ServerConnectionFactory.RMI);
		if (rmi == null) {
			stop();
		}
		rmi.start();
		socket = ServerConnectionFactory.getConnection(ServerConnectionFactory.SOCKET);
		if (socket == null) {
			stop();
		}
		socket.start();
		started = true;

		Application.println("Server ready...");
		while (true) {
			String line = null;
			try {
				line = Application.readLine("Press 'q' to exit, 'b' to broadcast");
			} catch (IOException e) {
				Application.exception(e, "Command not read", true);
				line = null;
			}
			if ("q".equals(line)) {
				stop();
			}
			if ("b".equals(line)) {
				broadcastEverybody("You are connected to the server");
			}
		}
	}

	/**
	 * This method stop the server. Incoming connections are not listened.
	 */
	public void stop() {
		if (rmi != null) {
			rmi.close();
		}
		if (socket != null) {
			socket.close();
		}
		Application.exitSuccess();
	}

	/**
	 * Registers a client on server.
	 *
	 * @param username
	 *            the username of the client
	 * @param client
	 *            the connection to the client
	 * @return the user if the connection is accepted. Otherwise return null.
	 */
	public User registerClientOnServer(String username, ClientConnection client) {
		if (!started) {
			return null;
		}
		for (User user : users) {
			if (user.getName().equalsIgnoreCase(username)) {
				if (user.isOnline()) {
					return null;
				} else {
					user.setConnection(client);
					return user;
				}
			}
		}
		User newUser;
		try {
			newUser = new User(username, client);
		} catch (RemoteException e) {
			Application.exception(e);
			return null;
		}
		setRoom(newUser);
		return newUser;
	}

	/**
	 * Adds the incoming client in the room, waiting for the game to start
	 *
	 * @param newUser
	 *            the new user
	 */
	private void setRoom(User newUser) {
		users.add(newUser);
		room.add(newUser);
		if (room.size() == Game.MAX_PLAYERS) {
			timeout.cancel();
			createNewGame();
		} else {
			timeout.cancel();
			timeout = new Timer();
			timeout.schedule(new TimerTask() {

				@Override
				public void run() {
					if (room.size() < Game.MIN_PLAYERS) {
						broadcastToRoom("Waiting for another player...", null);
						return;
					}
					createNewGame();

				}
			}, TIMEOUT_NEWGAME);
		}
		broadcastToRoom(newUser.getName() + " connected", newUser);
	}

	/**
	 * Creates the new game.
	 */
	private void createNewGame() {
		List<User> gamers = null;
		synchronized (room) {
			gamers = new ArrayList<User>(room);
			room.clear();
		}
		Game game = new Game(gamers);
		Thread t = new Thread(game, "GAME-" + games.size());
		games.put(game, t);
		t.start();
	}

	/**
	 * Broadcasts a message to current room.
	 *
	 * @param message
	 *            the message to broadcast
	 * @param excluded
	 *            the client to exclude in the broadcast
	 */
	private void broadcastToRoom(String message, User excluded) {
		for (User user : room) {
			if (user == excluded) {
				continue;
			}
			user.sendMessage(message);
		}
	}

	/**
	 * Broadcasts a message to everybody.
	 *
	 * @param message
	 *            the message to broadcast
	 */
	private void broadcastEverybody(String message) {
		for (User user : users) {
			user.sendMessage(message);
		}
	}

	/**
	 * Disconnects all the users in the game and remove the game just finished.
	 *
	 * @param game
	 *            the game
	 */
	public void gameOver(Game game) {
		if (started) {
			List<User> gone = new ArrayList<User>(game.getUsers());
			games.get(game).interrupt();
			games.remove(game);
			for (User user : gone) {
				disconnectUser(user);
			}
		}
	}

	/**
	 * Disconnects user from server.
	 *
	 * @param user
	 *            the user to be disconnected
	 */
	private void disconnectUser(User user) {
		user.disconnect();
		users.remove(user);
		Application.println(user.getName() + " disconnected from server");
	}
}
