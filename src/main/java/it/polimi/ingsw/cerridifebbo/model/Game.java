package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.Server;

import java.util.List;

public class Game implements Runnable{

	private static final int MAX_TURNS = 39;

	private final Server server;
	private final List<User> users;

	private GameState state;
	private Map map;
	private Deck deck;
	private int turn = 0;

	public Game(Server server, List<User> users) {
		this.server = server;
		this.state = new StartGame(this);
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	public GameState getState() {
		return state;
	}

	public void sendToUsers(String message) {
		server.broadcastPlayers(this, message);
	}

	public void sendToSpecificUser(User user, String message) {
		server.sendMessage(user, message);
	}

	public void nextTurn() {
		if (turn++ == MAX_TURNS) {
			state = new EndGame(this);
		} else {
			state = new Turn(this);
		}
		state.handle();
	}

	public void checkGame() {
		state = new CheckGame(this);
		state.handle();
	}

	public void endGame() {
		state = new EndGame(this);
		state.handle();
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public int getTurn() {
		return turn;
	}

	@Override
	public void run() {
		state.handle();
	}

	public void broadcastToPlayers(String message) {
		if (server == null) {
			return;
		}
		server.broadcastPlayers(this, message);
	}

	public void declareSector(Player player, Sector sector, boolean spotlight) {
		User found = null;
		for (User user : users) {
			if (user.getPlayer() == player) {
				found = user;
			}
		}
		if (server == null) {
			return;
		}
		server.declareSector(found, sector, spotlight);
	}

	public void askMoveFromUser(User user) {
		if (server == null) {
			return;
		}
		server.askMoveFromUser(user);
	}

	public void sendGameInformation(int size, Map map, User user) {
		if (server == null) {
			return;
		}
		server.sendGameInformation(size, map, user);
	}

	public boolean serverIsOn() {
		return server != null;
	}
}
