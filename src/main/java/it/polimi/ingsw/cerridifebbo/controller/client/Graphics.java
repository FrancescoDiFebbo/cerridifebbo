package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote.SectorRemote;

;

public abstract class Graphics {

	private NetworkInterface network;
	protected boolean initialized;

	public boolean isInitialized() {
		return initialized;
	}

	public abstract void initialize(MapRemote map, PlayerRemote player,
			int numberOfPlayers);

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

	public abstract void updatePlayerPosition(PlayerRemote player);

	public abstract void declareCard();

	public abstract void deletePlayerCard(PlayerRemote player, Card card);

	public abstract void addPlayerCard(PlayerRemote player, Card card);

	public abstract void updateEscapeHatch(MapRemote map, SectorRemote sector);
}
