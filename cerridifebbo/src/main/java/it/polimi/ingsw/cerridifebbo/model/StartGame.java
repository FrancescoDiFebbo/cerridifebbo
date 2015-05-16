package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class StartGame extends GameState {

	public StartGame(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		Map map = Map.getInstance();
		ArrayList<User> users = game.getUsers();
		Deck deck = new ConcreteDeckFactory().createDeck(users.size());
		try {
			initializePlayers(users, deck, map);
		} catch (Exception e) {
			// TODO Eventuali errori nel setting dei player si termina il game
		}
		game.setDeck(deck);
		game.setMap(map);
	}

	private void initializePlayers(ArrayList<User> users, Deck deck, Map map) throws Exception {
		for (User user : users) {
			Card card = deck.drawCharacterCard();
			Object player = card.performAction(null, map);
			if (player instanceof Player && player != null) {
				user.setPlayer((Player) player);
			} else {
				throw new Exception("Player not returned");
			}
			
		}
	}

	@Override
	public void perform(User user, String action, Object target) {
		try {
			throw new Exception("Move from user " + user + " not listened");
		} catch (Exception e) {
		}
	}

}
