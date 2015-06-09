package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.model.Game.Sentence;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Turn extends GameState {

	public Turn(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		List<User> userList = game.getUsers();
		for (User user : userList) {
			turn(user);
			game.checkGame();
			if (game.getState() instanceof EndGame) {
				break;
			}
		}
		game.nextTurn();
	}

	private void turn(User user) {
		PlayerTurnState state = new PlayerTurnState();
		Player player = user.getPlayer();
		if (canPlay(player)) {
			if (game.serverIsOn()) {
				user.getConnection().startTurn(user);
				// state.timeoutMove = new Timer();
				// state.timeoutMove.schedule(new MoveTimer(user),
				// Game.MAX_TIMEOUT);
			}
			while (!state.finish) {
				Move move = getMove(user);
				try {
					perform(user, move, state);
				} catch (IllegalMoveException e) {
					Application.exception(e);
					String error = e.getError();
					if (game.serverIsOn()) {
						user.getConnection().sendMessage(user, error);
					}
				}
			}
			if (user.getConnection() != null) {
				user.getConnection().endTurn(user);
			}
			user.clear();
		}
	}

	private Move getMove(User user) {
		Move move = null;
		if (game.serverIsOn()) {
			user.getConnection().askForMove(user);
			do {
				move = user.getMove();
			} while (move == null);
		} else {
			move = new Move(Move.TIMEFINISHED, null);
		}
		return move;
	}

	private boolean canPlay(Player player) {
		if (!player.isAlive()) {
			return false;
		}
		if (player instanceof HumanPlayer && !((HumanPlayer) player).isEscaped()) {
			return true;
		}
		return true;
	}

	private void perform(User user, Move move, PlayerTurnState state) {
		String action = move.getAction();
		String target = move.getTarget();
		switch (action) {
		case Move.MOVEMENT:
			movement(user, target, state);
			break;
		case Move.ATTACK:
			attack(user, state);
			break;
		case Move.USEITEMCARD:
			useCard(user, target);
			break;
		case Move.FINISH:
			finish(state);
			break;
		case Move.TIMEFINISHED:
			timeFinished(user, state);
			break;
		default:
			break;
		}
	}

	private void finish(PlayerTurnState state) {
		if (state.noMoreMovement) {
			endPlayerTurn(state);
		} else {
			throw new IllegalMoveException("Before finish you must make a movement");
		}
	}

	private void timeFinished(User user, PlayerTurnState state) {
		user.setTimeFinished(true);
		if (!state.noMoreMovement) {
			movement(user, randomReachableSector(user.getPlayer()).toString(), state);
		}
		game.informPlayers(user.getPlayer(), Sentence.TIMEFINISHED, null);
		endPlayerTurn(state);

	}

	private Sector randomReachableSector(Player player) {
		Random random = new Random();
		return player.getPosition().getReachableSectors(player.getMaxMovement())
				.get(random.nextInt(player.getPosition().getReachableSectors(player.getMaxMovement()).size()));
	}

	private void movement(User user, String target, PlayerTurnState state) throws IllegalMoveException {
		Sector destination = game.getMap().getCell(target);
		if (destination != null && !state.noMoreMovement && user.getPlayer().movement(destination, game)) {
			state.noMoreMovement = true;
			if (user.getPlayer() instanceof HumanPlayer && ((HumanPlayer) user.getPlayer()).isEscaped()) {
				endPlayerTurn(state);
			}
		} else {
			throw new IllegalMoveException("You can't move");
		}
	}

	private void attack(User user, PlayerTurnState state) throws IllegalMoveException {
		if (state.noMoreMovement && !state.noMoreAttack) {
			user.getPlayer().attack(game);
			state.noMoreAttack = true;
		} else {
			throw new IllegalMoveException("You can't attack");
		}
	}

	private void useCard(User user, String target) throws IllegalMoveException {
		if (user.getPlayer() instanceof AlienPlayer) {
			throw new IllegalMoveException("Alien can't use item. Aliens are stupid!");
		}
		Card selectedCard = null;
		for (Card own : user.getPlayer().getOwnCards()) {
			if (own.toString().equalsIgnoreCase(target)) {
				selectedCard = own;
				break;
			}
		}
		if (selectedCard != null && !(selectedCard instanceof DefenseItemCard)) {
			if (selectedCard instanceof SpotlightItemCard) {
				Sector sector = game.retrieveSector(user.getPlayer());
				selectedCard.performAction(user.getPlayer(), sector, game);
			}
			selectedCard.performAction(user.getPlayer(), null, game);
		} else {
			throw new IllegalMoveException("You are using a wrong item card");
		}
	}

	private void endPlayerTurn(PlayerTurnState state) {
		//state.timeoutMove.cancel();
		state.finish = true;
	}

	private class PlayerTurnState {
		private boolean finish = false;
		private boolean noMoreMovement = false;
		private boolean noMoreAttack = false;
		private Timer timeoutMove;
	}

	private class MoveTimer extends TimerTask {

		private final User user;

		MoveTimer(User user) {
			this.user = user;
		}

		@Override
		public void run() {
			user.putMove(new Move(Move.TIMEFINISHED, null));
		}

	}

}
