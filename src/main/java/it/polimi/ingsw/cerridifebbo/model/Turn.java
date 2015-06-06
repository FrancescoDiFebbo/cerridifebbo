package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;

import java.util.List;
import java.util.Random;

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
		}
		game.nextTurn();
	}

	private void turn(User user) {
		PlayerTurnState state = new PlayerTurnState();
		Player player = user.getPlayer();
		if (canPlay(player)) {
			while (!state.finish) {
				Move move = checkForServer(user);
				try {
					perform(player, move, state);
				} catch (IllegalMoveException e) {
					state.finish = true;
				}
			}
			if (player instanceof HumanPlayer) {
				((HumanPlayer) player).clear();
			}
		}
	}

	private Move checkForServer(User user) {
		Move move = null;
		if (game.serverIsOn()) {
			user.getConnection().askMoveFromUser(user);
			do {
				move = user.getMove();
			} while (move == null);
		} else {
			move = new Move(Move.TIMEFINISHED, null, null);
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

	private void perform(Player player, Move move, PlayerTurnState state) throws IllegalMoveException {
		String action = move.getAction();
		Sector target = move.getTarget();
		switch (action) {
		case Move.MOVEMENT:
			Sector sector = randomReachableSector(player);
			movement(player, sector, state);

			break;
		case Move.ATTACK:
			attack(player, state);
			break;
		case Move.USEITEMCARD:
			useCard(player, target, move.getSelectedCard());
			break;
		case Move.FINISH:
			endPlayerTurn(state);
			break;
		case Move.TIMEFINISHED:
			endPlayerTurn(state);
			break;
		default:
			break;
		}
	}

	private Sector randomReachableSector(Player player) {
		Random random = new Random();
		return player.getPosition().getReachableSectors(player.getMaxMovement())
				.get(random.nextInt(player.getPosition().getReachableSectors(player.getMaxMovement()).size()));
	}

	private void movement(Player player, Object target, PlayerTurnState state) throws IllegalMoveException {
		if (player.movement((Sector) target, game) && !state.noMoreMovement) {
			state.noMoreMovement = true;
		} else {
			throw new IllegalMoveException();
		}
	}

	private void attack(Player player, PlayerTurnState state) throws IllegalMoveException {
		if (state.noMoreMovement) {
			player.attack(game);
		} else {
			throw new IllegalMoveException();
		}
	}

	private void useCard(Player player, Sector target, Card card) throws IllegalMoveException {
		if (target != null && !(card instanceof DefenseItemCard)) {
			Card itemCard = card;
			itemCard.performAction(player, target, game);
		} else {
			throw new IllegalMoveException();
		}
	}

	private void endPlayerTurn(PlayerTurnState state) {
		state.finish = true;
	}

	private class PlayerTurnState {
		private boolean finish = false;
		private boolean noMoreMovement = false;
	}

}
