package it.polimi.ingsw.cerridifebbo.controller.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteServer extends Remote {

	public boolean registerOnServer(String username, String address, int port) throws RemoteException;
}
