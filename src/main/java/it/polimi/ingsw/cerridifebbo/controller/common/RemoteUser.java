package it.polimi.ingsw.cerridifebbo.controller.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUser extends Remote {

	public void sendMove(String action, String target) throws RemoteException;

}
