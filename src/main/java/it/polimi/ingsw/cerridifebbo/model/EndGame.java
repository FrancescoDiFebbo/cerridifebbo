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
		boolean allHumanKilled = true;
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (!player.isAlive()) {
				user.sendMessage("YOU LOSE");
			} else if (player instanceof HumanPlayer && ((HumanPlayer) player).isEscaped()) {
				user.sendMessage("YOU WIN");
			} else if (player instanceof HumanPlayer && player.isAlive()){
				allHumanKilled = false;
			}
		}
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (allHumanKilled) {
				if (player instanceof AlienPlayer && player.isAlive()) {
					user.sendMessage("YOU WIN");
				}
			} else {
				if (player instanceof AlienPlayer && player.isAlive()) {
					user.sendMessage("YOU LOSE");
				} else if (player instanceof HumanPlayer && player.isAlive()) {
					user.sendMessage("YOU WIN");
				}
			}
		}
	}
}
