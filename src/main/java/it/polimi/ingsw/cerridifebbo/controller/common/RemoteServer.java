package it.polimi.ingsw.cerridifebbo.controller.common;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface RemoteServer extends Remote {

	public static final String RMI_ID = "remote_server";

	public boolean registerClientOnServer(UUID id, int port) throws RemoteException;

	public void sendMessage(UUID client, String message) throws RemoteException;
	

}
