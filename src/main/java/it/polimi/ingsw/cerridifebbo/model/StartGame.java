package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class StartGame extends GameState {

	public StartGame(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		Map map = Map.getInstance();
		game.setMap(map);
		ArrayList<User> users = game.getUsers();
		Deck deck = new ConcreteDeckFactory().createDeck(users.size());
		game.setDeck(deck);
		try {
			initializePlayers();
		} catch (Exception e) {
			// TODO Eventuali errori nel setting dei player si termina il game
			game.endGame();
		}		
		game.nextTurn();
	}

	private void initializePlayers() throws Exception {
		for (User user : game.getUsers()) {
			Card card = game.getDeck().drawCharacterCard();
			Object player = card.performAction(null, null, game);
			if (player instanceof Player && player != null) {
				user.setPlayer((Player) player);
			} else {
				throw new Exception("Player not returned");
			}

		}
	}
}
