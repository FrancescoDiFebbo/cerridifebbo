package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Turn extends GameState {

	public Turn(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		ArrayList<User> userList = game.getUsers();
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
				Move move = game.getMoveFromUser(user);
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

	private boolean canPlay(Player player) {
		return player.isAlive() && (player instanceof HumanPlayer && !((HumanPlayer) player).isEscaped());
	}

	private void perform(Player player, Move move, PlayerTurnState state) throws IllegalMoveException {
		String action = move.getAction();
		Sector target = move.getTarget();
		switch (action) {
		case Move.MOVEMENT:
			movement(player, target, state);
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

	private void movement(Player player, Object target, PlayerTurnState state) throws IllegalMoveException {
		if (player.movement((Sector) target, null) && !state.noMoreMovement) {
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
			Card itemCard = (Card) card;
			itemCard.performAction(player, target, game);
		} else {
			throw new IllegalMoveException();
		}
	}

	private void endPlayerTurn(PlayerTurnState state) {
		state.finish = true;
	}

	private class PlayerTurnState {
		public boolean finish = false;
		public boolean noMoreMovement = false;
	}
}
