package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class RMIInterface implements NetworkInterface {

	private RemoteServer server;
	
	public boolean connect() {
        Registry registry;
		try {
			registry = LocateRegistry.getRegistry(Connection.SERVER_REGISTRY_PORT);
		} catch (RemoteException e) {			
			e.printStackTrace();
			return false;
		}
        try {
			server = (RemoteServer) registry.lookup(RemoteServer.RMI_ID);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean close() {
		return true;
	}

	@Override
	public void registerOnServer(UUID id, int port) throws RemoteException {
		server.registerOnServer(id, port);		
	}

	@Override
	public void sendMessage(UUID client, String message) throws RemoteException {
		server.sendMessage(client, message);
		
	}
}
