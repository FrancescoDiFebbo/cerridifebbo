package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Turn extends GameState {

	private final String MOVEMENT = "MOVEMENT";
	private final String ATTACK = "ATTACK";
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
					// TODO
					perform(u, actionUser, targetUser);
				}
			}
		}
		game.setTurn(game.getTurn() + 1);
	}

	@Override
	public void perform(User user, String action, Object target) {
		if (action == MOVEMENT && !noMoreMovement) {
			if (user.getPlayer().movement((Sector) target)) {
				noMoreMovement = true;
				Card drawnCard = user.getPlayer().getPos().playerEnters(game.getDeck());
				drawnCard.performAction();
			}
		} else if (action == ATTACK) {
			ArrayList<User> userList = game.getUsers();
			user.getPlayer().attack(userList);
		} else if (action == FINISH) {
			finish = true;
		} else if (action == TIMEFINISHED) {
			finish = true;
		}
	}
}
