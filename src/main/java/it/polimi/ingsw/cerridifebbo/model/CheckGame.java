package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.User;

public class CheckGame extends GameState {

	public CheckGame(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		Player lastHuman = null;
		for (User user : game.getUsers()) {
			Player p = user.getPlayer();
			if (p instanceof HumanPlayer) {
				if (lastHuman == null) {
					lastHuman = p;
				} else {
					lastHuman = null;
					break;
				}
			}
		}
		game.setLastHuman(lastHuman);
		boolean allAlienKilled = true;
		boolean allHumanNotInGame = true;
		for (User u : game.getUsers()) {
			if (u.getPlayer() instanceof HumanPlayer) {
				HumanPlayer human = (HumanPlayer) u.getPlayer();
				if (human.isAlive() && !human.isEscaped()) {
					allHumanNotInGame = false;

				}
			} else if (u.getPlayer() instanceof AlienPlayer) {
				AlienPlayer alien = (AlienPlayer) u.getPlayer();
				if (alien.isAlive()) {
					allAlienKilled = false;

				}
			}
		}
		if (allHumanNotInGame || allAlienKilled) {
			game.setEnd(true);
		}
	}
}
