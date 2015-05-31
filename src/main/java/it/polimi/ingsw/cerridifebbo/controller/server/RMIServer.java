package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteClient;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServer extends ServerConnection {
	private static final Logger LOG = Logger.getLogger(ServerConnection.class.getName());

	private Registry registry;
	private RemoteServer remoteServer;
	private Map<UUID, RemoteClient> clients = new HashMap<UUID, RemoteClient>();

	public RMIServer(Server server) {
		super(server);
	}

	@Override
	public void start() throws RemoteException {
		remoteServer = new ServerImpl(this);
		registry = LocateRegistry.createRegistry(Connection.SERVER_REGISTRY_PORT);
		try {
			registry.bind(RemoteServer.RMI_ID, remoteServer);
		} catch (AlreadyBoundException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			Application.exitError();
		}
	}

	@Override
	public void close() throws RemoteException {
		if (registry != null) {
			try {
				registry.unbind(RemoteServer.RMI_ID);
			} catch (NotBoundException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
				Application.exitError();
			}
		}
	}

	@Override
	public boolean registerClientOnServer(UUID id, Object clientInterface) {
		int port = (Integer) clientInterface;
		RemoteClient client;
		try {
			Registry clientRegistry = LocateRegistry.getRegistry(port);
			client = (RemoteClient) clientRegistry.lookup(RemoteClient.RMI_ID);
		} catch (RemoteException | NotBoundException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
		clients.put(id, client);
		LOG.info("Client " + id + " connected at port " + clientInterface);
		server.registerClientOnServer(id, this);
		return true;
	}

	@Override
	public String getMoveFromUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String string, UUID selected) throws RemoteException {
		if (selected == null) {
			for (UUID id : clients.keySet()) {
				clients.get(id).sendMessage(string);
			}
		} else {
			clients.get(selected).sendMessage(string);
		}
	}


	@Override
	public void sendGameInformation(int size, it.polimi.ingsw.cerridifebbo.model.Map map, User user) {
		try {
			clients.get(user.getId()).sendGameInformation(size, map, user.getPlayer());
		} catch (RemoteException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}	
	}

}
