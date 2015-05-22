package it.polimi.ingsw.cerridifebbo.controller.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import it.polimi.ingsw.cerridifebbo.controller.common.RemoteClient;

public class ClientImpl extends UnicastRemoteObject implements RemoteClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ClientImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

}
