package it.polimi.ingsw.cerridifebbo.controller.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {

	public static final String RMI_ID = "it.polimi.ingsw.cerridifebbo.remote_client";

	public void sendMessage(String message) throws RemoteException;

}
