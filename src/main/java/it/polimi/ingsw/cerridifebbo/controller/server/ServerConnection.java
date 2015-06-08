package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.util.UUID;

public interface ServerConnection extends Runnable {

	public abstract void start();

	public abstract void close();

	public abstract boolean registerClientOnServer(UUID id, Object clientInterface);
	
	public abstract void sendGameInformation(User user, int size, Map map);
	
	public abstract void startTurn(User user);
	
	public abstract void endTurn(User user);

	public abstract void askForMove(User user);
	
	public abstract void askForSector(User user);
	
	public abstract void updatePlayer(User user, Card card, boolean added);
	
	public abstract void sendMessage(User user, String string);	

	public abstract void disconnectUser(User user);

	public abstract boolean poke(User user);

}
