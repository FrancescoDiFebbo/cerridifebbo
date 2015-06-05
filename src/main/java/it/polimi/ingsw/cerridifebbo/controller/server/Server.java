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

import javax.management.timer.TimerMBean;

public class Server {
	private static final Logger LOG = Logger.getLogger(Server.class.getName());

	private ServerConnection rmi, socket;
	private Map<User, ServerConnection> users = new HashMap<User, ServerConnection>();
	private List<Game> games = new ArrayList<Game>();
	private List<User> room = new ArrayList<User>();
	private Timer timeout = new Timer();
	private Map<User, Timer> timers = new HashMap<User, Timer>();

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
		try {
			rmi.close();
			socket.close();
			Application.exitSuccess();
		} catch (IOException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
			Application.exitError();
		}
	}

	public void registerClientOnServer(UUID idClient, ServerConnection connection) {
		User newUser = new User(idClient);
		users.put(newUser, connection);
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
	}

	@SuppressWarnings("unchecked")
	private void createNewGame() {
		List<User> gamers = (List<User>)((ArrayList<User>)room).clone();
		room.clear();
		Game game = new Game(this, gamers);
		games.add(game);		
		game.run();
	}

	private void broadcastToRoom(String message, User excluded){
		for (User user : room) {
			if (user == excluded) {
				continue;
			}
			try {
				users.get(user).sendMessage(message, user.getId());
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}
	
	private void broadcastEverybody(String message) {
		for (User user : users.keySet()) {
			try {
				users.get(user).sendMessage(message, user.getId());
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
		
	}
	
	public void broadcastPlayers(Game game, String message){
		for (User user : game.getUsers()) {
			try {
				users.get(user).sendMessage(message, user.getId());
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}		}
	}

	public void declareSector(User user, Sector sector, boolean spotlight) {
		if (sector == null) {
			users.get(user).askForSector(user);
		}
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

	public void sendGameInformation(int size, it.polimi.ingsw.cerridifebbo.model.Map map, User user) {
		users.get(user).sendGameInformation(size, map, user);
		
	}

	public void askMoveFromUser(User user) {
		users.get(user).askMoveFromUser(user, 10);
		Timer timer = new Timer();
		timers.put(user, timer);
		timer.schedule(new UserTimer(user), 10000);
		
	}
	
	private class UserTimer extends TimerTask {
		
		User user;
		
		UserTimer(User user){
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
}
