package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Game {
	private String name;
	private int turn;

	public int getTurn() {
		return turn;
	}

	public void nextTurn() {
		this.turn++;
	}

	private GameState state;
	private Map map;
	private Deck deck;
	private final ArrayList<User> users;

	public ArrayList<User> getUsers() {
		return users;
	}

	public Game(ArrayList<User> users) {
		this.state = new StartGame(this);
		this.users = users;
	}

	public void setState(GameState state) {
		this.state = state;
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

	public void run() {
		state.handle();
	}

	public void declareSector(Player player, Sector sector) {
		// TODO se sector è uguale a null il metodo chiederà al controller il
		// settore da raggiungere altrimenti il controller
		// si occuperà di mostrare il settore dichiarato
	}

	public Move getMoveFromUser(User user) {
		// TODO questo metodo chiederà al controller di ricevere un move dallo
		// user selezionato e restituira un oggetto Move		
		return new Move(Move.MOVEMENT, map.getCell(5, 5));
	}
}