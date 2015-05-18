package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Turn extends GameState {

	private boolean finish;
	private boolean noMoreMovement;

	public Turn(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		ArrayList<User> userList = game.getUsers();
		for (User u : userList) {
			if (u.getPlayer().isAlive()) {
				finish = false;
				noMoreMovement = false;
				while (!finish) {
					Move move = game.getMoveFromUser(u);
					try {
						perform(u.getPlayer(), move);
					} catch (Exception e) {
						//TODO gestire errore nell'esecuzione della mossa
						game.setState(new EndGame(game));
						game.run();
						return;
					}
				}
			}
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
				Card drawnCard = player.getPosition().playerEnters(game.getDeck());
				drawnCard.performAction(player, game);
			} else {
				throw new Exception("Invalid movement");
			}
			break;
		case Move.ATTACK:
			player.attack(game);
			break;
		case Move.USEITEMCARD:
			if (target instanceof Card && target != null) {
				Card itemCard = (Card) target;
				itemCard.performAction(player, game);
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
