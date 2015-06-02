package it.polimi.ingsw.cerridifebbo.model;

import java.util.List;

public class StartGame extends GameState {
	public StartGame(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		Map map = Map.getInstance();
		game.setMap(map);
		List<User> users = game.getUsers();
		Deck deck = new ConcreteDeckFactory().createDeck(users.size());
		game.setDeck(deck);
		initializePlayers();
		game.broadcastToPlayers("Game is starting...");
		game.nextTurn();
	}

	private void initializePlayers() {
		for (User user : game.getUsers()) {
			Card card = game.getDeck().drawCharacterCard();
			Object player = card.performAction(null, null, game);
			if (player instanceof Player && player != null) {
				user.setPlayer((Player) player);
			} else {
				throw new NullPointerException("Player not returned");
			}
			game.sendGameInformation(game.getUsers().size(), game.getMap(), user);
		}
	}
}
