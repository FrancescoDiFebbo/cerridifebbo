package it.polimi.ingsw.cerridifebbo.model;

public interface Card {

	public abstract Object performAction(Player player, Object target, Game game);
	
}
