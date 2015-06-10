package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;

public interface ServerConnection extends Runnable {

	public abstract void start();

	public abstract void close();

	public abstract boolean registerClientOnServer(String username, ClientConnection client);

}
