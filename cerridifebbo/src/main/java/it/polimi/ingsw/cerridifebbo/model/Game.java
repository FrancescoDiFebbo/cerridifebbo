package it.polimi.ingsw.cerridifebbo.model;

public class Game {
	private String name;
	private Player[] playerList;
	private int turn;

	private GameState state;
	private Map map;
	private Deck deck;	

	Game(){
		state = new StartGame(this);
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
	
	public void run(){
		state.handle();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.run();
	}
}
