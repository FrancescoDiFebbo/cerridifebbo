package it.polimi.ingsw.cerridifebbo.model;

public class StartGame extends GameState {
	
	public StartGame(Game game){
		super(game);
	}
	
	
	@Override
	public void handle() {
		Map map = Map.getInstance();
		Deck deck = new ConcreteDeckFactory().createDeck();
	}
}
