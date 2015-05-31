package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public interface RemoteClient extends Remote {

	public static final String RMI_ID = "it.polimi.ingsw.cerridifebbo.remote_client";

	public void sendMessage(String message) throws RemoteException;

	public void sendGameInformation(int size, Map map, Player player) throws RemoteException;

}
