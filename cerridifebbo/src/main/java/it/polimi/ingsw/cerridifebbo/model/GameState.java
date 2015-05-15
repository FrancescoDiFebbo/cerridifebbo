package it.polimi.ingsw.cerridifebbo.model;

public abstract class GameState {
	
	protected Game game;
	
	public GameState(Game game){
		this.game= game;
	}
	
	public abstract void handle();
	public abstract void perform(User user, String action, Object target);
}
