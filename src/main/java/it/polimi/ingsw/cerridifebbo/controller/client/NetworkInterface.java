package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;

public interface NetworkInterface {

	public void connect();

	public void close();

	public boolean registerClientOnServer();

	public void sendToServer(String action, String target);

	public void setGraphicInterface(Graphics graphics);

	public void setGameInformation(MapRemote map, PlayerRemote player, int size);

	public void setPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added);

	public void setHatchUpdate(MapRemote map, SectorRemote sector);
}
