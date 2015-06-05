package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

public abstract class Graphics {

	private NetworkInterface network;
	protected boolean initialized;

	public boolean isInitialized() {
		return initialized;
	}

	public abstract void initialize(Map map, Player player);

	public abstract void sendMessage(String message);

	public abstract void startTurn();

	public abstract void endTurn();

	public void setNetworkInterface(NetworkInterface network) {
		this.network = network;
	}

	public NetworkInterface getNetworkInterface() {
		return network;
	}

	public abstract void declareMove();

	public abstract void declareSector();

	public abstract void updatePlayerPosition(Player player);

	public abstract void declareCard();

	public abstract void deletePlayerCard(Player player, Card card);

	public abstract void addPlayerCard(Player player, Card card);
}
