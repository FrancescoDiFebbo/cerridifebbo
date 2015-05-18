package it.polimi.ingsw.cerridifebbo.model;

public interface Card {

	public abstract Object performAction(Player target, Game game) throws Exception;

}
