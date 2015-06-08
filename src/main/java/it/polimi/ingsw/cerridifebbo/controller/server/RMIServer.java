package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteClient;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIServer extends UnicastRemoteObject implements ServerConnection, RemoteServer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(ServerConnection.class.getName());
	private static final int MAX_ATTEMPTS = 5;

	private transient Registry registry;
	private transient Thread thread;
	private transient Map<User, RemoteClient> clients = new HashMap<User, RemoteClient>();
	private final transient Server server;

	public RMIServer(Server server) throws RemoteException {
		this.server = server;
	}

	@Override
	public void run() {
		try {
			registry = LocateRegistry.createRegistry(Connection.SERVER_REGISTRY_PORT);
			registry.bind(RemoteServer.RMI_ID, this);
		} catch (RemoteException | AlreadyBoundException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			Application.exitError();
		}
	}

	@Override
	public void start() {
		thread = new Thread(this, "RMI_SERVER");
		thread.start();
	}

	@Override
	public void close() {
		if (registry != null) {
			try {
				registry.unbind(RemoteServer.RMI_ID);
			} catch (NotBoundException | RemoteException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
				Application.exitError();
			}
		}
		if (thread != null) {
			thread.interrupt();
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
		User newUser = server.registerClientOnServer(id, this);
		clients.put(newUser, client);
		try {
			clients.get(newUser).sendMessage("You are connected to the server");
		} catch (RemoteException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}
		Application.println("Client " + id + " connected at port " + clientInterface);
		return true;
	}

	@Override
	public void askForMove(User user) {
		int attempts = 0;
		while (attempts < MAX_ATTEMPTS) {
			try {
				clients.get(user).askForMove();
				break;
			} catch (RemoteException e) {
				Application.exception(e);
				attempts++;
				server.disconnectUser(user);
			}
		}
	}

	@Override
	public void askForSector(User user) {
		try {
			clients.get(user).askForSector();
		} catch (RemoteException e) {
			Application.exception(e);
		}
	}

	@Override
	public void sendMessage(User user, String message) {
		try {
			clients.get(user).sendMessage(message);
		} catch (RemoteException e) {
			Application.exception(e);
		}
	}

	@Override
	public void sendGameInformation(User user, int size, it.polimi.ingsw.cerridifebbo.model.Map map) {
		try {
			clients.get(user).sendGameInformation(size, map, user.getPlayer());
		} catch (RemoteException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}
	}

	@Override
	public void updatePlayer(User user, Card card, boolean added) {
		try {
			clients.get(user).updatePlayer(user.getPlayer(), card, added);
		} catch (RemoteException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}

	}

	@Override
	public boolean registerOnServer(UUID id, int port) throws RemoteException {
		return registerClientOnServer(id, port);
	}

	@Override
	public void sendMessage(UUID client, String message) throws RemoteException {
		Application.println(client.toString().split("-")[0] + ") " + message);
	}

	@Override
	public void sendMove(UUID id, String action, String target) throws RemoteException {
		for (User user : clients.keySet()) {
			if (user.getId().equals(id)) {
				user.putMove(new Move(action, target));
				return;
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public void startTurn(User user) {
		try {
			clients.get(user).startTurn();
		} catch (RemoteException e) {
			Application.exception(e);
		}

	}

	@Override
	public void endTurn(User user) {
		try {
			clients.get(user).endTurn();
		} catch (RemoteException e) {
			Application.exception(e);
		}
	}

	@Override
	public void disconnectUser(User user) {
		try {
			clients.get(user).disconnect();
		} catch (RemoteException e) {
			Application.exception(e);
		}
		clients.remove(user);

	}

	@Override
	public boolean poke(User user) {
		try {
			return clients.get(user).poke();
		} catch (RemoteException e) {
			Application.exception(e);
			return false;
		}
	}

}
