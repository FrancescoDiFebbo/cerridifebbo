package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.io.IOException;

public interface NetworkInterface {

	/**
	 * @throws IOException
	 */
	public void connect() throws IOException;

	/**
	 * @throws IOException
	 */
	public void close() throws IOException;
	
	/**
	 * @return
	 */
	public boolean registerClientOnServer();

	public void sendToServer(String move);
	
	public void setGameInformation(Map map, Player player);
	
	public void setGraphicInterface(Graphics graphics);
	
}
