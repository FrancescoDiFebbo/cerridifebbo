package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;

/**
 * The Interface ServerConnection provides methods to manage a server for
 * different connection protocols.
 * 
 * @author cerridifebbo
 */
public interface ServerConnection extends Runnable {

	/**
	 * Starts the server. It listens for incoming connection from client.
	 */
	public abstract void start();

	/**
	 * Closes the server. Incoming connections are not listened.
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
