package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.User;

import java.util.List;

public class StartGame extends GameState {
	public StartGame(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		game.informPlayers(null, Sentence.STARTING_GAME, null);
		Map map = Map.getInstance();
		game.setMap(map);
		List<User> users = game.getUsers();
		Deck deck = new ConcreteDeckFactory().createDeck(users.size());
		game.setDeck(deck);
		initializePlayers();
	}

	private void initializePlayers() {
		for (User user : game.getUsers()) {
			Card card = game.getDeck().drawCharacterCard();
			Player player = (Player) card.performAction(null, null, game);
			if (player != null) {
				user.setPlayer(player);
				if (game.getUsers().size() == 2) {
					player.setRevealed();
				}
			} else {
				throw new NullPointerException("Player not returned");
			}
			user.sendGameInformation(game);
		}
	}
}
