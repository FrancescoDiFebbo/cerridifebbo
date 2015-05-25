package it.polimi.ingsw.cerridifebbo.controller.server;

import java.io.IOException;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.UUID;

public interface ServerConnection {
	
	public void start() throws IOException, AlreadyBoundException;

	public void close() throws AccessException, NotBoundException, IOException;

	public void registerClientOnServer(UUID idClient);
	
}
