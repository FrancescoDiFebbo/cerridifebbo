package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Turn extends GameState {

	private final String MOVEMENT = "MOVEMENT";
	private final String ATTACK = "ATTACK";
	private final String FINISH = "FINISH";
	private final String TIMEFINISHED = "TIMEFINISHED";
	private boolean finish;

	public Turn(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		ArrayList<User> userList = game.getUsers();
		for (User u : userList) {
			if (u.getPlayer().isAlive()) {
				finish = false;
				while (!finish) {
					String actionUser = "";
					Sector targetUser = null;
					// TODO
					perform(u, actionUser, targetUser);
				}
			}
		}

	}

	@Override
	public void perform(User user, String action, Object target) {
		if (action == MOVEMENT) {
			user.getPlayer().movement((Sector) target);
		}
		if (action == ATTACK) {
			ArrayList<User> userList = game.getUsers();
			user.getPlayer().attack(userList);
		}
		if (action == FINISH) {
			finish = true;
		}
		if (action == TIMEFINISHED) {
			finish = true;
		}
	}
}
