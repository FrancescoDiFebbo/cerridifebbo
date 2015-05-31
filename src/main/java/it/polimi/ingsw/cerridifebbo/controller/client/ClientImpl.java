package it.polimi.ingsw.cerridifebbo.controller.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteClient;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

public class ClientImpl extends UnicastRemoteObject implements RemoteClient {

	private final UUID id;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ClientImpl(UUID id) throws RemoteException {
		super();
		this.id = id;		
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		Application.println("SERVER) " + message);

	}

	public UUID getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public void sendGameInformation(int size, Map map, Player player) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

}
