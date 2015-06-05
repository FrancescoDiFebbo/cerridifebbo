package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

public interface NetworkInterface {

	public void connect();

	public void close();

	public boolean registerClientOnServer();

	public void sendToServer(String action, String target);

	public void setGameInformation(Map map, Player player);

	public void setGraphicInterface(Graphics graphics);

}
