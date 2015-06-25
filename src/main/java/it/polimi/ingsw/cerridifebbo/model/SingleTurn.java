package it.polimi.ingsw.cerridifebbo.model;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import it.polimi.ingsw.cerridifebbo.controller.server.User;

/**
 * The Class SingleTurn manages the player turn.
 *
 * @author cerridifebbo
 */
public class SingleTurn extends GameState {

	/** The Constant MAX_CARDS. */
	private static final int MAX_CARDS = 3;

	/** The user playing the current turn. */
	private final User user;

	/** The player associated to user. */
	private final Player player;

	/** Indicates if player finished his turn */
	private boolean finish = false;

	/** Indicates if player has no more movements to perform */
	private boolean noMoreMovement = false;

	/** Indicates if player has no more attacks to perform */
	private boolean noMoreAttack = false;

	/** Indicates if player has to use a item card. */
	private boolean mustUseCard = false;

	/** The timer of turn */
	private Timer timeoutMove;

	/**
	 * Instantiates a new single turn state.
	 *
	 * @param game
	 *            the game
	 * @param user
	 *            the user
	 */
	public SingleTurn(Game game, User user) {
		super(game);
		this.user = user;
		this.player = user.getPlayer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.model.GameState#handle()
	 */
	@Override
	public void handle() {
		if (canPlay()) {
			user.startTurn();
			timeoutMove = new Timer();
			timeoutMove.schedule(new TimerTask() {

				@Override
				public void run() {
					user.setTimeFinished(true);

				}
			}, Game.MAX_TIMEOUT);
			while (!finish) {
				Move move = user.getMove();
				perform(move);
			}
			timeoutMove.cancel();
		}
	}

	/**
	 * Returns if the player can play.
	 *
	 * @return true, if successful
	 */
	private boolean canPlay() {
		if (!player.isAlive()) {
			return false;
		}
		if (player instanceof HumanPlayer && ((HumanPlayer) player).isEscaped()) {
			return false;
		}
		return true;
	}

	/**
	 * Performs the move from user.
	 *
	 * @param move
	 *            the move
	 */
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

	/**
	 * Performs a movement of player
	 *
	 * @param target
	 *            the target
	 */
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

	/**
	 * If the player's deck is full, the user has to choose an action.
	 */
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

	/**
	 * Performs an attack in position of player.
	 *
	 * @param target
	 *            the target
	 */
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

	/**
	 * Performs the use of an item card.
	 *
	 * @param target
	 *            the target
	 */
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

	/**
	 * Performs the remove of an item card.
	 *
	 * @param target
	 *            the target
	 */
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

	/**
	 * Finds an item card in the deck by a String.
	 *
	 * @param target
	 *            the target
	 * @return the card
	 */
	private Card findCard(String target) {
		for (Card own : player.getOwnCards()) {
			if (own.toString().equalsIgnoreCase(target)) {
				return own;
			}
		}
		return null;
	}

	/**
	 * Performs a random movement if time is finished
	 */
	private void timeFinished() {
		game.informPlayers(player, Sentence.TIMEFINISHED, null);
		if (!noMoreMovement) {
			movement(randomReachableSector().toString());
		}
		finish();
	}

	/**
	 * Returns a random reachable sector.
	 *
	 * @return the sector
	 */
	private Sector randomReachableSector() {
		Random random = new Random();
		return player.getPosition().getReachableSectors(player.getMaxMovement())
				.get(random.nextInt(player.getPosition().getReachableSectors(player.getMaxMovement()).size()));
	}

	/**
	 * Checks if turn can end.
	 */
	private void finish() {
		if (noMoreMovement && !mustUseCard) {
			endTurn();
		} else if (!noMoreMovement) {
			user.sendMessage("Before finish you must make a movement");
		} else if (mustUseCard) {
			user.sendMessage("Before finish you must use or discard a card");
		}
	}

	/**
	 * Ends turn.
	 */
	private void endTurn() {
		timeoutMove.cancel();
		finish = true;
		user.endTurn();
		user.clear();
	}
}
