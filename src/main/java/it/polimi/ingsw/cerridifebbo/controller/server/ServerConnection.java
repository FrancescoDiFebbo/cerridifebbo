package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.model.User;

import java.io.IOException;
import java.util.UUID;

public abstract class ServerConnection {
	
	protected final Server server;
	
	public ServerConnection(Server server) {
		this.server = server;
	}
	
	public abstract void start() throws IOException;

	public abstract void close() throws IOException;

	public abstract boolean registerClientOnServer(UUID id, Object clientInterface);
	
	public abstract String getMoveFromUser(User user);

	public abstract void sendMessage(String string, UUID selected) throws IOException;
	
}
