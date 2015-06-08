package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClient extends Remote {

	public static final String RMI_ID = "it.polimi.ingsw.cerridifebbo.remote_client";

	public void sendMessage(String message) throws RemoteException;

	public void sendGameInformation(int size, Map map, Player player) throws RemoteException;

	public void askForMove() throws RemoteException;

	public void updatePlayer(Player player, Card card, boolean added) throws RemoteException;

	public void startTurn() throws RemoteException;

	public void endTurn() throws RemoteException;

	public void disconnect() throws RemoteException;

	public boolean poke() throws RemoteException;

	public void askForSector() throws RemoteException;

}
