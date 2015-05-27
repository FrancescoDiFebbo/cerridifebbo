package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteClient;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RMIServer extends ServerConnection {

	private Registry registry;
	private RemoteServer remoteServer;
	private Map<UUID, RemoteClient> clients = new HashMap<UUID, RemoteClient>();

	public RMIServer(Server server) {
		super(server);
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
	public boolean registerClientOnServer(UUID id, int port) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry(port);
		RemoteClient client;
		try {
			client = (RemoteClient) registry.lookup(RemoteClient.RMI_ID);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		clients.put(id, client);
		System.out.println("Client " + id + " connected at port " + port);
		server.registerClientOnServer(id, this);
		return true;
	}

	@Override
	public void run() {
		try {
			start();
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Closing server...");
			e.printStackTrace();
			// TODO gestire chiusura programma
			System.exit(-1);
		}
	}

}
