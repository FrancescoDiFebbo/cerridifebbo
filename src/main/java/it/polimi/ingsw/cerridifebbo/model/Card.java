package it.polimi.ingsw.cerridifebbo.model;

import java.io.Serializable;

public interface Card extends Serializable{

	public abstract Object performAction(Player player, Object target, Game game);

}
