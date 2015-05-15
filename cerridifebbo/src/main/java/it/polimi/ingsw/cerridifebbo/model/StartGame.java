package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class StartGame extends GameState {

	public StartGame(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		Map map = Map.getInstance();
		Deck deck = new ConcreteDeckFactory().createDeck();
		ArrayList<Player> players = new ArrayList<Player>();
	}

	@Override
	public void perform(User user, String action, Object target) {

	}

}
