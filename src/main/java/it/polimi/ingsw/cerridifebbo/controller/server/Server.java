package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
	private static final Logger LOG = Logger.getLogger(Server.class.getName());

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		new Server().run();
	}

	private static final int MAX_PLAYERS = 8;
	private static final int WAITING_FOR_USERS = 20000;

	// private Timer timeout = new Timer();
	private ServerConnection rmi, socket;
	private Thread rmiThread, socketThread;
	private Map<User, ServerConnection> users = new HashMap<User, ServerConnection>();
	private List<Game> games = new ArrayList<Game>();
	private List<User> buildingMatch = new ArrayList<User>();

	private Object busy = new Object();

	public void run() {
		rmi = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.RMI);
		try {
			rmi.start();
		} catch (AlreadyBoundException | IOException e) {
			LOG.log(Level.WARNING, "Closing server...", e);
			return;
		}
		System.out.println("RMI server is started...");

		socket = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.SOCKET);
		socketThread = new Thread((Runnable) socket);
		socketThread.start();
		System.out.println("Socket server is started...\nServer is ready! :)");
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("Press 'q' to exit");
			String line = in.nextLine();
			if (line.equals("q")) {
				try {
					rmi.close();
					socket.close();
					break;
				} catch (NotBoundException | IOException e) {
					LOG.log(Level.WARNING, "", e);
					return;
				}
			}
		}
	}

	public void registerClientOnServer(UUID idClient, ServerConnection connection) {
		User newUser = new User(idClient);
		users.put(newUser, connection);
		buildingMatch.add(newUser);
		if (buildingMatch.size() < MAX_PLAYERS) {
			// timeout.cancel();
			// timeout = new Timer();
			// timeout.schedule(new TimerTask() {
			//
			// @Override
			// public void run() {
			// //createNewGame();
			//
			// }
			// }, WAITING_FOR_USERS);
		} else {
			// timeout.cancel();
			createNewGame();
		}
	}

	private void createNewGame() {
		Game game = new Game(this, buildingMatch);
		games.add(game);
		for (User user : buildingMatch) {
		}
		buildingMatch.clear();
		System.out.println("Starting new game");
		game.run();
	}

	public Move getMoveFromUser(User user) {
		// TODO
		return null;
	}

	public void declareSector(User user, Sector sector, boolean spotlight) {
		// TODO se sector è uguale a null il metodo chiederà al controller il
		// settore da raggiungere altrimenti il controller
		// si occuperà di mostrare il settore dichiarato
	}
}
