package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Turn extends GameState {

	private boolean finish;
	private boolean noMoreMovement;
	private boolean adrenalineUsed;
	private boolean sedativesUsed;

	public Turn(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		ArrayList<User> userList = game.getUsers();
		for (User u : userList) {
			if (u.getPlayer().isAlive()) {
				adrenalineUsed = false;
				finish = false;
				sedativesUsed = false;
				noMoreMovement = false;
				while (!finish) {
					Move move = game.getMoveFromUser(u);
					try {
						perform(u.getPlayer(), move);
					} catch (Exception e) {
						// TODO gestire errore nell'esecuzione della mossa
						game.setState(new EndGame(game));
						game.run();
						return;
					}
				}
				if (adrenalineUsed) {
					u.getPlayer().setMaxMovement(HumanPlayer.HUMANMOVEMENT);
				}
			}
			game.setState(new CheckGame(game));
			game.run();
		}
		game.nextTurn();
	}

	private void perform(Player player, Move move) throws Exception {
		String action = move.getAction();
		Object target = move.getTarget();
		switch (action) {
		case Move.MOVEMENT:
			if (player.movement((Sector) target) && !noMoreMovement) {
				noMoreMovement = true;
				if (!sedativesUsed) {
					Card drawnCard = player.getPosition().playerEnters(
							game.getDeck());
					if (drawnCard != null)
						drawnCard.performAction(player, game);
				}
			} else {
				throw new Exception("Invalid movement");
			}
			break;
		case Move.ATTACK:
			player.attack(game);
			break;
		case Move.USEITEMCARD:
			if (target instanceof Card && target != null
					&& !(target instanceof DefenseItemCard)) {
				Card itemCard = (Card) target;
				itemCard.performAction(player, game);
				if (itemCard instanceof AdrenalineItemCard)
					adrenalineUsed = true;
				else if (itemCard instanceof SedativesItemCard)
					sedativesUsed = true;
			} else {
				throw new Exception("invalid card");
			}
			break;
		case Move.FINISH:
			finish = true;
			break;
		case Move.TIMEFINISHED:
			finish = true;
			break;

		default:
			return;
		}
	}
}
