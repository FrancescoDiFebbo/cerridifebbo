package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Game {
	private String name;
	private ArrayList<Player> playerList;
	private int turn;

	private GameState state;
	private Map map;
	private Deck deck;
	private final ArrayList<User> users;

	Game(ArrayList<User> users) {
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
	
	public void perform(User user, String action, Object object){
		state.perform(user, action, object);
	}

	public static void main(String[] args) {
		
	}
}
