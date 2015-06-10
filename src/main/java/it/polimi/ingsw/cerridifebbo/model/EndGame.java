package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.User;

public class EndGame extends GameState {

	public EndGame(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		for (User user : game.getUsers()) {
			user.sendMessage("GAME OVER");
		}
	}
}
