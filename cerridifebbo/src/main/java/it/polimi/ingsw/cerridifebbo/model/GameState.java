package it.polimi.ingsw.cerridifebbo.model;

public abstract class GameState {
	
	protected Game game;
	
	public GameState(Game game){
		this.game= game;
	}
	
	abstract void handle();
}
