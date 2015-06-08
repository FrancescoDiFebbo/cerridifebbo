package it.polimi.ingsw.cerridifebbo.model;

public class EndGame extends GameState {

	public EndGame(Game game) {
		super(game);
	}

	@Override
	public void handle() {
		if (game.serverIsOn()) {
			for (User user : game.getUsers()) {
				user.getConnection().sendMessage(user, "GAME OVER");
			}
		}		
	}
}
