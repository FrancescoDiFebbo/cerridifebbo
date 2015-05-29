package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.model.CharacterDeckFactory;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
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
		} catch (IOException | AlreadyBoundException e) {
			LOG.log(Level.SEVERE, "RMI server error. Closing.", e);
			Application.exitError();
		}

		// Starting socket server
		socket = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.SOCKET);
		try {
			socket.start();
		} catch (IOException | AlreadyBoundException e1) {
			LOG.log(Level.SEVERE, "Socket server error. Closing.", e1);
			Application.exitError();
		}

		// Server ready
		LOG.log(Level.INFO, "Server ready :)");
		while (true) {
			String line = readLine("Press 'q' to exit");
			if ("q".equals(line)) {
				try {
					rmi.close();
					socket.close();
					break;
				} catch (NotBoundException | IOException e) {
					LOG.log(Level.WARNING, "", e);
					Application.exitError();
				}
			}
		}
		Application.exitSuccess();
	}

	private String readLine(String format, Object... args) {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		LOG.info(String.format(format, args));

		BufferedReader br = null;
		InputStreamReader isr = null;
		String read = null;

		isr = new InputStreamReader(System.in);
		br = new BufferedReader(isr);
		try {
			read = br.readLine();
		} catch (IOException e) {
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
	}
}
