package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

public interface NetworkInterface {

	public void connect();

	public void close();

	public boolean registerClientOnServer();

	public void sendToServer(String action, String target);

	public void setGraphicInterface(Graphics graphics);

	public void setGameInformation(Map map, Player player, int size);

	public void updatePlayer(Player player, Card card, boolean added);

	public String chooseUsername();
}
