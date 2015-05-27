package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.Server;

import java.util.ArrayList;

public class Game {
	
	private static final int MAX_TURNS = 39;
	
	private final Server server;	
	private final ArrayList<User> users;
	
	private GameState state;
	private Map map;
	private Deck deck;
	private int turn = 0;

	public Game(Server server, ArrayList<User> users) {
		this.server = server;
		this.state = new StartGame(this);
		this.users = users;
	}

	public ArrayList<User> getUsers() {
		return users;
	}

	public GameState getState(){
		return state;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public int getTurn() {
		return turn;
	}

	public void nextTurn() {
		if (turn++ == MAX_TURNS) {
			state = new EndGame(this);
		} else {
			state = new Turn(this);
		}
		state.handle();
	}
	
	public void checkGame(){
		state = new CheckGame(this);
		state.handle();
	}
	
	public void endGame() {
		state = new EndGame(this);
		state.handle();
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public void run() {
		state.handle();
	}

	public void declareSector(Player player, Sector sector, boolean spotlight) {
		server.declareSector(null, null);
	}

	public Move getMoveFromUser(User user) {
		return new Move(Move.MOVEMENT, map.getCell(7, 9), null);
		//return server.getMoveFromUser(user);
	}
}
