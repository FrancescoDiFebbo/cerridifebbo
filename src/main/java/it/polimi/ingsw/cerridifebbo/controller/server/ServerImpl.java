package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerImpl extends UnicastRemoteObject implements RemoteServer {

	Map<UUID, Integer> clients = new HashMap<UUID, Integer>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerOnServer(UUID id, int port) {
		clients.put(id, port);
		System.out.println("Client " + id + " connected at port " + port);
	}

	@Override
	public void sendMessage(UUID client, String message) {
		System.out.println(client.toString().split("-")[0] + ") " + message);

	}

}
