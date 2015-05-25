package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.model.Game;
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

public class Server {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		new Server();
	}

	private static final int MAX_PLAYERS = 8;
	private static final int WAITING_FOR_USERS = 20000;

	// private Timer timeout = new Timer();
	private ServerConnection rmi, socket;
	private Map<User, ServerConnection> users = new HashMap<User, ServerConnection>();
	private List<Game> games = new ArrayList<Game>();
	private ArrayList<User> buildingMatch = new ArrayList<User>();

	Server() {
		rmi = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.RMI);
		try {
			rmi.start();
		} catch (AlreadyBoundException | IOException e) {
			System.err.println("Closing server...");
			e.printStackTrace();
			return;
		}		
		System.out.println("RMI server is started...");

		socket = ServerConnectionFactory.getConnection(this, ServerConnectionFactory.SOCKET);
		new Thread((Runnable) socket).start();
		System.out.println("Socket server is started...\nServer is ready! :)");
		Scanner in = new Scanner(System.in);
		while(true){
			System.out.println("Press 'q' to exit");
			String line = in.nextLine();
			if(line.equals("q")){
				try {
					rmi.close();
					socket.close();
					break;
				} catch (NotBoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
		System.exit(0);
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
}
