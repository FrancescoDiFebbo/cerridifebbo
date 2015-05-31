package it.polimi.ingsw.cerridifebbo.controller.client;

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
	
}
