package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public class ServerImpl extends UnicastRemoteObject implements RemoteServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient ServerConnection serverConnection;

	protected ServerImpl(ServerConnection serverConnection) throws RemoteException {
		super();
		this.serverConnection = serverConnection;
	}

	@Override
	public boolean registerClientOnServer(UUID id, int port) throws RemoteException {
		return serverConnection.registerClientOnServer(id, port);
	}

	@Override
	public void sendMessage(UUID client, String message) {
		Application.println(client.toString().split("-")[0] + ") " + message);
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
