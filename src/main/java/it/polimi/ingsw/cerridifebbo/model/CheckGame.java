package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.User;

/**
 * The Class CheckGame checks if, after a player's turn, the game is ended.
 *
 * @see GameState
 * @author cerridifebbo
 */
public class CheckGame extends GameState {

	/**
	 * Instantiates a new check game state.
	 *
	 * @param game
	 *            the game
	 */
	public CheckGame(Game game) {
		super(game);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.model.GameState#handle()
	 */
	@Override
	public void handle() {
		checkLastHuman();
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

	/**
	 * Checks if there is only one human in the game. If it's true, sets the
	 * field lastHuman in game.
	 */
	private void checkLastHuman() {
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
	}
}
