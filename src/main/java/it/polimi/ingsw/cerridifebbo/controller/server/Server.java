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

public class Server {
	private static final int TIMEOUT_NEWGAME = 10000;
	private static Server instance = new Server();

	private ServerConnection rmi, socket;
	private List<User> users = new ArrayList<User>();
	private List<User> room = new ArrayList<User>();
	private Map<Game, Thread> games = new HashMap<Game, Thread>();
	private Timer timeout = new Timer();
	private boolean started = false;

	private Server() {

	}

	public static void main(String[] args) {
		Server.getInstance().start();
	}

	public static Server getInstance() {
		return instance;
	}

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

	public void stop() {
		if (rmi != null) {
			rmi.close();
		}
		if (socket != null) {
			socket.close();
		}
		Application.exitSuccess();
	}

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

	private void broadcastToRoom(String message, User excluded) {
		for (User user : room) {
			if (user == excluded) {
				continue;
			}
			user.sendMessage(message);
		}
	}

	private void broadcastEverybody(String message) {
		for (User user : users) {
			user.sendMessage(message);
		}
	}

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

	private void disconnectUser(User user) {
		user.disconnect();
		users.remove(user);
		Application.println(user.getName() + " disconnected from server");
	}
}
