package it.polimi.ingsw.cerridifebbo.controller.common;

import java.rmi.RemoteException;

public interface ClientConnection {

	public void sendMessage(String message) throws RemoteException;

	public void startTurn() throws RemoteException;

	public void endTurn() throws RemoteException;

	public void askForMove() throws RemoteException;

	public void askForSector() throws RemoteException;

	public void askForCard() throws RemoteException;

	public void sendGameInformation(MapRemote map, PlayerRemote player, int size) throws RemoteException;

	public void sendPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added) throws RemoteException;

	public void sendHatchUpdate(MapRemote map, SectorRemote sector) throws RemoteException;

	public void disconnect() throws RemoteException;
}
