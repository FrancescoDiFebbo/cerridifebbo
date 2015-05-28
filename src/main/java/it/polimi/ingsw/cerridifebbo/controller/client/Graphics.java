package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

public abstract class Graphics {

	Client client;

	public abstract void initialize(Map map, Player player);

	public abstract void sendMessage(String message);

	public abstract void startTurn();

	public abstract void endTurn();

	public void setClient(Client client) {
		this.client = client;
	}

	public abstract String declareMove(Player player);

	public void sendToClient(String move) {

	}
}
