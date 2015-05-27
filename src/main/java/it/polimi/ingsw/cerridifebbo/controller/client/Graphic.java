package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

public abstract class Graphic {
	public abstract void initialize(Map map, Player player);

	public abstract void sendMessage(String message);
	
	
}
