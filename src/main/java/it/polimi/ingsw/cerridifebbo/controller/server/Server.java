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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	private static final Logger LOG = Logger.getLogger(Server.class.getName());

	private ServerConnection rmi, socket;
	private Map<User, ServerConnection> users = new HashMap<User, ServerConnection>();
	private List<Game> games = new ArrayList<Game>();
	private List<User> buildingMatch = new ArrayList<User>();

	public static void main(String[] args) {
		new Server().run();
	}

	public void run() {
		// Starting RMI server
		rmi = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.RMI);
		try {
			rmi.start();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			Application.exitError();
		}

		// Starting socket server
		socket = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.SOCKET);
		try {
			socket.start();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			Application.exitError();
		}

		// Server ready
		Application.print("Server ready :)");
		while (true) {
			String line = readLine("Press 'q' to exit");
			if ("q".equals(line)) {
				stop();
			}
			if ("d".equals(line)) {
				declareSector((User) users.keySet().toArray()[0], null, false);
			}
		}

	}

	public void stop() {
		try {
			rmi.close();
			socket.close();
			Application.exitSuccess();
		} catch (IOException e) {
			LOG.log(Level.WARNING, "", e);
			Application.exitError();
		}
	}

	private String readLine(String format, Object... args) {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		Application.print(String.format(format, args));

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

	public void registerClientOnServer(UUID idClient, ServerConnection connection) {
		User newUser = new User(idClient);
		users.put(newUser, connection);
		buildingMatch.add(newUser);
		if (buildingMatch.size() == CharacterDeckFactory.MAX_PLAYERS) {
			createNewGame();
		}
	}

	private void createNewGame() {
		Game game = new Game(this, buildingMatch);
		games.add(game);
		buildingMatch.clear();
		LOG.info("Starting new game!");
		game.run();
	}

	public Move getMoveFromUser(User user) {
		users.get(user).getMoveFromUser(user);
		return null;
	}

	public void declareSector(User user, Sector sector, boolean spotlight) {
		// TODO se sector è uguale a null il metodo chiederà al controller il
		// settore da raggiungere altrimenti il controller
		// si occuperà di mostrare il settore dichiarato
		for (User u : users.keySet()) {
			try {
				users.get(u).sendMessage("Alieno avvistato nel settore E15", u.getId());
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}
}
