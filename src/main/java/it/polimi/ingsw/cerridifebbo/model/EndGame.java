package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.User;

/**
 * The Class EndGame takes care of victory messages to send to players.
 *
 * @author cerridifebbo
 */
public class EndGame extends GameState {

	/** The Constant WIN. */
	private static final String WIN = "YOU WIN";
	/** The Constant LOSE. */
	private static final String LOSE = "YOU LOSE";

	/**
	 * Instantiates a new end game state.
	 *
	 * @param game
	 *            the game
	 */
	public EndGame(Game game) {
		super(game);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.model.GameState#handle()
	 */
	@Override
	public void handle() {
		for (User user : game.getUsers()) {
			user.sendMessage("GAME OVER");
		}
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (!player.isAlive()) {
				user.sendMessage(LOSE);
			} else if (player instanceof HumanPlayer && ((HumanPlayer) player).isEscaped()) {
				user.sendMessage(WIN);
			}
		}
		HumanPlayer lastHuman = (HumanPlayer) game.getLastHuman();
		if (lastHuman == null) {
			aliensResult(true);
		} else if (lastHuman.isEscaped()) {
			aliensResult(false);
		} else if (!lastHuman.isAlive()){
			aliensResult(true);
		} else {
			checkRaces();
		}

	}

	private void checkRaces() {
		boolean allAlienKilled = true;
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (player instanceof AlienPlayer && player.isAlive()) {
				allAlienKilled = false;
				break;
			}
		}
		if (allAlienKilled) {
			aliensResult(false);
		} else {
			aliensResult(true);
		}
		
	}

	/**
	 * According to param "win" the method sends victory message to players.
	 *
	 * @param win
	 *            victory of the aliens
	 */
	private void aliensResult(boolean win) {
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (player instanceof AlienPlayer && player.isAlive()) {
				user.sendMessage(win ? WIN : LOSE);
			} else if (player instanceof HumanPlayer && player.isAlive()) {
				user.sendMessage(win ? LOSE : WIN);
			}
		}
	}
}
