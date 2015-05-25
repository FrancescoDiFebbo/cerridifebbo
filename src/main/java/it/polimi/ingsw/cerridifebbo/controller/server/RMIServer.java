package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.UUID;

public class RMIServer implements ServerConnection {

	private final Server server;
	RemoteServer remoteServer;
	Registry registry;

	RMIServer(Server server) {
		this.server = server;
	}

	@Override
	public void start() throws RemoteException, AlreadyBoundException {
		remoteServer = new ServerImpl(this);
		registry = LocateRegistry.createRegistry(Connection.SERVER_REGISTRY_PORT);
		registry.bind(RemoteServer.RMI_ID, remoteServer);
	}

	@Override
	public void close() throws AccessException, RemoteException, NotBoundException {
		registry.unbind(RemoteServer.RMI_ID);
	}

	@Override
	public void registerClientOnServer(UUID idClient) {
		server.registerClientOnServer(idClient, this);
	}

}
