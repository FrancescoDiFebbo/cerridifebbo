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
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (!player.isAlive()) {
				user.sendMessage("YOU LOSE");
			} else if (player instanceof HumanPlayer && ((HumanPlayer) player).isEscaped()) {
				user.sendMessage("YOU WIN");
			}
		}
		HumanPlayer lastHuman = (HumanPlayer) game.getLastHuman();
		if (lastHuman == null) {
			aliensResult(true);
		} else if (lastHuman.isEscaped()) {
			aliensResult(false);
		} else {
			aliensResult(true);
		}

	}

	private void aliensResult(boolean win) {
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (player instanceof AlienPlayer && player.isAlive()) {
				user.sendMessage(win ? "YOU WIN" : "YOU LOSE");
			}
		}

	}
}
