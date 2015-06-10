package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.User;
import it.polimi.ingsw.cerridifebbo.model.Game.Sentence;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Turn extends GameState {

	private static final int MAX_CARDS = 3;

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
			user.startTurn();
			state.timeoutMove = new Timer();
			state.timeoutMove.schedule(new MoveTimer(user), Game.MAX_TIMEOUT);
			while (!state.finish) {
				Move move = user.getMove();
				try {
					perform(user, move, state);
				} catch (IllegalMoveException e) {
					user.sendMessage(e);
				}
			}
		}
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
			useCard(user, target, state);
			break;
		case Move.FINISH:
			finish(user, state);
			break;
		case Move.TIMEFINISHED:
			timeFinished(user, state);
			break;
		case Move.DELETECARD:
			deleteCard(user, target, state);
			break;
		default:
			break;
		}
	}

	private void movement(User user, String target, PlayerTurnState state) {
		Sector destination = game.getMap().getCell(target);
		if (destination != null && !state.noMoreMovement && user.getPlayer().movement(destination, game)) {
			state.noMoreMovement = true;
			if (user.getPlayer().getOwnCards().size() > MAX_CARDS) {
				state.mustUseCard = true;
				while (state.mustUseCard) {
					Move move = user.getCard();
					switch (move.getAction()) {
					case Move.USEITEMCARD:
						useCard(user, move.getTarget(), state);
						break;
					case Move.DELETECARD:
						deleteCard(user, move.getTarget(), state);
						break;
					default:
						user.sendMessage("You must use or discard a card");
						break;
					}
				}
			}
			if (user.getPlayer() instanceof HumanPlayer && ((HumanPlayer) user.getPlayer()).isEscaped()) {
				endPlayerTurn(user, state);
			}
		} else {
			throw new IllegalMoveException("You can't move");
		}
	}

	private void attack(User user, PlayerTurnState state) {
		if (state.noMoreMovement && !state.noMoreAttack) {
			user.getPlayer().attack(game);
			state.noMoreAttack = true;
		} else {
			throw new IllegalMoveException("You can't attack");
		}
	}

	private void useCard(User user, String target, PlayerTurnState state) {
		if (user.getPlayer() instanceof AlienPlayer) {
			throw new IllegalMoveException("Alien can't use item. Aliens are stupid!");
		}
		Card selectedCard = findCard(user, target);
		if (selectedCard != null && !(selectedCard instanceof DefenseItemCard)) {
			if (selectedCard instanceof SpotlightItemCard) {
				Sector sector = user.getSector(game.getMap());
				selectedCard.performAction(user.getPlayer(), sector, game);
				state.mustUseCard = false;
			} else {
				selectedCard.performAction(user.getPlayer(), null, game);
				state.mustUseCard = false;
			}
		} else {
			throw new IllegalMoveException("You are using a wrong item card");
		}
	}

	private void deleteCard(User user, String target, PlayerTurnState state) {
		Card selectedCard = findCard(user, target);
		user.getPlayer().getOwnCards().remove(selectedCard);
		state.mustUseCard = false;
		user.updatePlayer(user.getPlayer(), selectedCard, false);
		game.informPlayers(user.getPlayer(), Sentence.DISCARD_CARD, null);
	}

	private Card findCard(User user, String target) {
		for (Card own : user.getPlayer().getOwnCards()) {
			if (own.toString().equalsIgnoreCase(target)) {
				return own;
			}
		}
		return null;
	}

	private void timeFinished(User user, PlayerTurnState state) {
		user.setTimeFinished(true);
		game.informPlayers(user.getPlayer(), Sentence.TIMEFINISHED, null);
		if (!state.noMoreMovement) {
			movement(user, randomReachableSector(user.getPlayer()).toString(), state);
		}
		finish(user, state);

	}

	private void finish(User user, PlayerTurnState state) {
		if (state.noMoreMovement && !state.mustUseCard) {
			endPlayerTurn(user, state);
		} else if (!state.noMoreMovement) {
			throw new IllegalMoveException("Before finish you must make a movement");
		} else if (state.mustUseCard) {
			throw new IllegalMoveException("Before finish you must use or discard a card");
		}
	}

	private Sector randomReachableSector(Player player) {
		Random random = new Random();
		return player.getPosition().getReachableSectors(player.getMaxMovement())
				.get(random.nextInt(player.getPosition().getReachableSectors(player.getMaxMovement()).size()));
	}

	private void endPlayerTurn(User user, PlayerTurnState state) {
		state.timeoutMove.cancel();
		state.finish = true;
		user.endTurn();
		user.clear();
	}

	private class PlayerTurnState {
		private boolean finish = false;
		private boolean noMoreMovement = false;
		private boolean noMoreAttack = false;
		private boolean mustUseCard = false;
		private Timer timeoutMove;
	}

	private class MoveTimer extends TimerTask {

		private final User user;

		MoveTimer(User user) {
			this.user = user;
		}

		@Override
		public void run() {
			user.setTimeFinished(true);
		}
	}
}
