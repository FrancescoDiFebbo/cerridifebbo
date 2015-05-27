package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerImpl extends UnicastRemoteObject implements RemoteServer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ServerConnection serverConnection;
	
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
		System.out.println(client.toString().split("-")[0] + ") " + message);

	}

	@Override
	public void broadcastMessage(String message) throws RemoteException {
		
	}

}
