package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.rmi.RemoteException;

public interface ClientConnection {

	public void sendMessage(String message) throws RemoteException;

	public void sendGameInformation(Map map, Player player, int size) throws RemoteException;

	public void startTurn() throws RemoteException;

	public void endTurn() throws RemoteException;

	public void askForMove() throws RemoteException;

	public void askForSector() throws RemoteException;

	public void askForCard() throws RemoteException;

	public void updatePlayer(Player player, Card card, boolean added) throws RemoteException;
	
	public void updatePlayer(PlayerRemote player) throws RemoteException;

	public void disconnect() throws RemoteException;
}
