package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Turn extends GameState {

	private final String MOVEMENT = "MOVEMENT";
	private final String ATTACK = "ATTACK";
	private final String USEITEMCARD = "USEITEMCARD";
	private final String FINISH = "FINISH";
	private final String TIMEFINISHED = "TIMEFINISHED";
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
					String actionUser = "";
					Sector targetUser = null;
					// TODO wait player's move
					try {
						perform(u, actionUser, targetUser);
					} catch (Exception e) {
						// TODO exception: inform player invalid move
					}
				}
			}
		}
		game.setTurn(game.getTurn() + 1);
	}

	@Override
	public void perform(User user, String action, Object target)
			throws Exception {
		if (action == MOVEMENT && !noMoreMovement) {
			if (user.getPlayer().movement((Sector) target)) {
				noMoreMovement = true;
				Card drawnCard = user.getPlayer().getPos()
						.playerEnters(game.getDeck());
				drawnCard.performAction(user.getPlayer(), game.getMap());
			} else {
				throw new Exception("Invalid movement");
			}
		} else if (action == ATTACK) {
			ArrayList<User> userList = game.getUsers();
			user.getPlayer().attack(userList);
		} else if (action == USEITEMCARD) {
			if (target instanceof Card && target != null) {
				Card itemCard = (Card) target;
				itemCard.performAction(user.getPlayer(), game.getMap());
			} else {
				throw new Exception("invalid card");
			}
		} else if (action == FINISH) {
			finish = true;
		} else if (action == TIMEFINISHED) {
			finish = true;
		} else {
			throw new Exception("invalid move");
		}
	}
}
