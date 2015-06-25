package it.polimi.ingsw.cerridifebbo.model;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.ingsw.cerridifebbo.controller.server.User;

public class SingleTurn extends GameState {

	private static final int MAX_CARDS = 3;

	private final User user;
	private final Player player;
	private boolean finish = false;
	private boolean noMoreMovement = false;
	private boolean noMoreAttack = false;
	private boolean mustUseCard = false;
	private Timer timeoutMove;

	public SingleTurn(Game game, User user) {
		super(game);
		this.user = user;
		this.player = user.getPlayer();
	}

	@Override
	public void handle() {
		if (canPlay()) {
			user.startTurn();
			timeoutMove = new Timer();
			timeoutMove.schedule(new MoveTimer(user), Game.MAX_TIMEOUT);
			while (!finish) {
				Move move = user.getMove();
				perform(move);
			}
			timeoutMove.cancel();
		}
	}

	private boolean canPlay() {
		if (!player.isAlive()) {
			return false;
		}
		if (player instanceof HumanPlayer && ((HumanPlayer) player).isEscaped()) {
			return false;
		}
		return true;
	}

	private void perform(Move move) {
		String action = move.getAction();
		String target = move.getTarget();
		switch (action) {
		case Move.MOVEMENT:
			movement(target);
			break;
		case Move.ATTACK:
			attack(target);
			break;
		case Move.USEITEMCARD:
			useCard(target);
			break;
		case Move.FINISH:
			finish();
			break;
		case Move.TIMEFINISHED:
			timeFinished();
			break;
		default:
			user.sendMessage("Move not valid");
			break;
		}
	}

	private void movement(String target) {
		Sector destination = game.getMap().getCell(target);
		if (destination != null && !noMoreMovement && player.movement(destination, game)) {
			player.drawSectorCard(destination, game);
			noMoreMovement = true;
			if (player.getOwnCards().size() > MAX_CARDS) {
				fullDeck();
			}
			if (player instanceof HumanPlayer && ((HumanPlayer) player).isEscaped()) {
				finish();
			}
		} else {
			user.sendMessage("You can't move");
		}
	}

	private void fullDeck() {
		mustUseCard = true;
		while (mustUseCard) {
			Move move = user.getCard();
			switch (move.getAction()) {
			case Move.USEITEMCARD:
				useCard(move.getTarget());
				break;
			case Move.DELETECARD:
				deleteCard(move.getTarget());
				break;
			default:
				user.sendMessage("You must use or discard a card");
				break;
			}
		}
	}

	private void attack(String target) {
		if (!noMoreMovement && !noMoreAttack) {
			Sector sector = game.getMap().getCell(target);
			while (sector == null) {
				sector = user.getSector(game.getMap());
			}
			if (player.movement(sector, game)) {
				user.updatePlayer(player, null, false);
				player.attack(game);
				noMoreAttack = true;
				noMoreMovement = true;
			} else {
				user.sendMessage("You can't attack");
			}
		} else {
			user.sendMessage("You can't attack");
		}
	}

	private void useCard(String target) {
		if (player instanceof AlienPlayer) {
			user.sendMessage("Alien can't use item. Aliens are stupid!");
			return;
		}
		Card selectedCard = findCard(target);
		if (selectedCard != null && !(selectedCard instanceof DefenseItemCard)) {
			if (selectedCard instanceof SpotlightItemCard) {
				Sector sector = null;
				do {
					sector = user.getSector(game.getMap());
				} while (sector == null);
				selectedCard.performAction(player, sector, game);

			} else {
				selectedCard.performAction(player, null, game);
			}
			mustUseCard = false;
		} else {
			user.sendMessage("You are using a wrong item card");
		}
	}

	private void deleteCard(String target) {
		Card selectedCard = findCard(target);
		if (selectedCard == null) {
			user.sendMessage("Card not valid");
			return;
		}
		player.getOwnCards().remove(selectedCard);
		((ItemCard) selectedCard).setTaken(false);
		mustUseCard = false;
		user.updatePlayer(player, selectedCard, false);
		game.informPlayers(player, Sentence.DISCARD_CARD, null);
	}

	private Card findCard(String target) {
		for (Card own : player.getOwnCards()) {
			if (own.toString().equalsIgnoreCase(target)) {
				return own;
			}
		}
		return null;
	}

	private void timeFinished() {
		game.informPlayers(player, Sentence.TIMEFINISHED, null);
		if (!noMoreMovement) {
			movement(randomReachableSector().toString());
		}
		finish();
	}

	private Sector randomReachableSector() {
		Random random = new Random();
		return player.getPosition().getReachableSectors(player.getMaxMovement())
				.get(random.nextInt(player.getPosition().getReachableSectors(player.getMaxMovement()).size()));
	}

	private void finish() {
		if (noMoreMovement && !mustUseCard) {
			endTurn();
		} else if (!noMoreMovement) {
			user.sendMessage("Before finish you must make a movement");
		} else if (mustUseCard) {
			user.sendMessage("Before finish you must use or discard a card");
		}
	}

	private void endTurn() {
		timeoutMove.cancel();
		finish = true;
		user.endTurn();
		user.clear();
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
