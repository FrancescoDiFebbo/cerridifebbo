package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.model.CharacterDeckFactory;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	private static final Logger LOG = Logger.getLogger(Server.class.getName());

	private ServerConnection rmi, socket;
	private List<User> users = new ArrayList<User>();
	private Map<Game, Thread> games = new HashMap<Game, Thread>();
	private List<User> room = new ArrayList<User>();
	private Timer timeout = new Timer();
	private Map<User, Timer> timers = new HashMap<User, Timer>();

	public static void main(String[] args) {
		new Server().start();
	}

	public void start() {
		// Starting RMI server
		rmi = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.RMI);
		if (rmi == null) {
			return;
		}
		rmi.start();

		// Starting socket server
		socket = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.SOCKET);
		if (socket == null) {
			return;
		}
		socket.start();

		// Server ready
		Application.println("Server ready :)");
		while (true) {
			String line = readLine("Press 'q' to exit, 'b' to broadcast, 'h' to heartbeat");
			if ("q".equals(line)) {
				stop();
			}
			if ("b".equals(line)) {
				broadcastEverybody("You are connected to the server");
			}
			if ("h".equals(line)) {
				heartBeat();
			}
		}
	}

	public void stop() {
		rmi.close();
		socket.close();
		Application.exitSuccess();
	}

	public User registerClientOnServer(UUID idClient, ServerConnection connection) {
		User newUser = new User(idClient, connection);
		users.add(newUser);
		room.add(newUser);
		timeout.cancel();
		timeout = new Timer();
		timeout.schedule(new TimerTask() {

			@Override
			public void run() {
				if (room.size() == 1) {
					broadcastToRoom("Waiting for another player...", null);
					return;
				}
				createNewGame();

			}
		}, 10000);
		broadcastToRoom("New player connected", newUser);
		if (room.size() == CharacterDeckFactory.MAX_PLAYERS) {
			timeout.cancel();
			createNewGame();
		}
		return newUser;
	}

	private void createNewGame() {
		List<User> gamers = new ArrayList<User>(room);
		room.clear();
		Game game = new Game(this, gamers);
		Thread t = new Thread(game, "GAME" + String.valueOf(games.size()));
		games.put(game, t);
		t.start();
	}

	private void broadcastToRoom(String message, User excluded) {
		for (User user : room) {
			if (user == excluded) {
				continue;
			}
			user.getConnection().sendMessage(user, message);
		}
	}

	private void broadcastEverybody(String message) {
		for (User user : users) {
			user.getConnection().sendMessage(user, message);
		}
	}

	public void sendGameInformation(int size, it.polimi.ingsw.cerridifebbo.model.Map map, User user) {
		user.getConnection().sendGameInformation(user, size, map);
	}

	public void askMoveFromUser(User user) {
		user.getConnection().askForMove(user);
	}

	public List<User> getUsers() {
		return users;
	}

	private class UserTimer extends TimerTask {

		User user;

		UserTimer(User user) {
			super();
			this.user = user;
		}

		@Override
		public void run() {
			user.putMove(new Move(Move.TIMEFINISHED, null));
		}

	}

	private String readLine(String format, Object... args) {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		Application.println(String.format(format, args));

		BufferedReader br = null;
		InputStreamReader isr = null;
		String read = null;

		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		try {
			read = br.readLine();
		} catch (IOException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
			read = null;
		}
		return read;
	}

	public void gameOver(Game game) {
		List<User> gone = new ArrayList<User>(game.getUsers());
		games.get(game).interrupt();
		games.remove(game);
		for (User user : gone) {
			user.getConnection().disconnectUser(user);
			users.remove(user);
			LOG.info(user.getId().toString().split("-")[0] + " disconnected from server");
		}
	}

	private void heartBeat() {
		List<User> temp = new ArrayList<User>(users);
		for (User user : temp) {
			if (!user.getConnection().poke(user)) {
				user.setOnline(false);
				user.getConnection().disconnectUser(user);
				users.remove(user);
				Application.println(user.getId().toString().split("-")[0] + " disconnected");
			}
		}
	}

	public void disconnectUser(User user) {
		user.getConnection().disconnectUser(user);
	}
}
