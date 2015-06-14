package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;

// TODO: Auto-generated Javadoc
/**
 * The Interface ServerConnection.
 * 
 * @author cerridifebbo
 */
public interface ServerConnection extends Runnable {

	/**
	 * Starts the server. It listens to incoming connection from client.
	 */
	public abstract void start();

	/**
	 * Closes the server.
	 */
	public abstract void close();

	/**
	 * Registers a client on server.
	 *
	 * @param username
	 *            the username
	 * @param client
	 *            the client connection
	 * @return true, if successful
	 */
	public abstract boolean registerClientOnServer(String username, ClientConnection client);

}
