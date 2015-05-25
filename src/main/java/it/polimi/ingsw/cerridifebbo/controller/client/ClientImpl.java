package it.polimi.ingsw.cerridifebbo.controller.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import it.polimi.ingsw.cerridifebbo.controller.common.RemoteClient;

public class ClientImpl extends UnicastRemoteObject implements RemoteClient {

	private final UUID id;
	private int port;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ClientImpl(UUID id, int port) throws RemoteException {
		super();
		this.id = id;
		this.port = port;
		
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		System.out.println("SERVER) " + message);

	}

	public UUID getId() {
		return id;
	}

}
