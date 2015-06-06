package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.model.CharacterDeckFactory;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Sector;
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
	private List<Thread> games = new ArrayList<Thread>();
	private List<User> room = new ArrayList<User>();
	private Timer timeout = new Timer();
	private Map<User, Timer> timers = new HashMap<User, Timer>();

	public static void main(String[] args) {
		new Server().run();
	}

	public void run() {
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
			String line = readLine("Press 'q' to exit");
			if ("q".equals(line)) {
				stop();
			}
			if ("b".equals(line)) {
				broadcastEverybody("You are connected to the server");
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
		Thread t = new Thread(game);
		games.add(t);
		t.start();
	}

	private void broadcastToRoom(String message, User excluded) {
		for (User user : room) {
			if (user == excluded) {
				continue;
			}
			try {
				user.getConnection().sendMessage(user, message);
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

	private void broadcastEverybody(String message) {
		for (User user : users) {
			try {
				user.getConnection().sendMessage(user, message);
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}

	}

	public void sendMessage(User user, String message) {
		try {
			user.getConnection().sendMessage(user, message);
		} catch (IOException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}
	}

	public void broadcastPlayers(Game game, String message) {
		for (User user : game.getUsers()) {
			try {
				user.getConnection().sendMessage(user, message);
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

	public void declareSector(User user, Sector sector, boolean spotlight) {
		if (sector == null) {
			user.getConnection().askForSector(user);
		}
		// TODO se sector è uguale a null il metodo chiederà al controller il
		// settore da raggiungere altrimenti il controller
		// si occuperà di mostrare il settore dichiarato

		for (User u : users) {
			try {
				user.getConnection().sendMessage(user, "Alieno avvistato nel settore E15");
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

	public void sendGameInformation(int size, it.polimi.ingsw.cerridifebbo.model.Map map, User user) {
		user.getConnection().sendGameInformation(user, size, map);
	}

	public void askMoveFromUser(User user) {
		user.getConnection().askMoveFromUser(user);
		// Timer timer = new Timer();
		// timers.put(user, timer);
		// timer.schedule(new UserTimer(user), 10000);

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
			user.putMove(new Move(Move.TIMEFINISHED, null, null));
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

	public List<Thread> getGames() {
		return games;
	}
}
