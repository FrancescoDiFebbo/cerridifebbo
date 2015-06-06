package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.io.IOException;
import java.util.UUID;

public interface ServerConnection extends Runnable{

	public abstract void start();

	public abstract void close();

	public abstract boolean registerClientOnServer(UUID id, Object clientInterface);

	public abstract void askMoveFromUser(User user);

	public abstract void sendMessage(User user, String string) throws IOException;

	public abstract void sendGameInformation(User user, int size, Map map);

	public abstract void sendMove(User user, String action, String target);

	public abstract void askForSector(User user);

}
