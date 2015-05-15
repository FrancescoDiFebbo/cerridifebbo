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
		initializePlayers(users, deck, map);
		game.setDeck(deck);
		game.setMap(map);
	}

	private void initializePlayers(ArrayList<User> users, Deck deck, Map map) {
		for (User user : users) {
			Card character = deck.drawCharacterCard();
			Player player;
			if (character instanceof HumanCard) {
				player = new HumanPlayer((CharacterCard) character, map.getHumanSector());
				user.setPlayer(player);
			} else if (character instanceof AlienCard) {
				player = new AlienPlayer((CharacterCard) character, map.getAlienSector());
				user.setPlayer(player);
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
