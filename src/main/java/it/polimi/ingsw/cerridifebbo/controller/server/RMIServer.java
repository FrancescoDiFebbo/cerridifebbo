package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteClient;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * The Class RMIServer.
 * 
 * @author cerridifebbo
 *
 */
public class RMIServer extends UnicastRemoteObject implements ServerConnection, RemoteServer {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The instance of RMIServer. */
	private static transient RMIServer instance;

	/** The registry of RMIServer. */
	private transient Registry registry;

	/** The thread containing RMIServer. */
	private transient Thread thread;

	/**
	 * Instantiates a new RMI server.
	 *
	 * @throws RemoteException
	 *             the remote exception
	 */
	private RMIServer() throws RemoteException {
		super();
	}

	/**
	 * Gets the single instance of RMIServer.
	 *
	 * @return single instance of RMIServer
	 */
	public static RMIServer getInstance() {
		if (instance == null) {
			try {
				return new RMIServer();
			} catch (RemoteException e) {
				Application.exception(e);
				return null;
			}
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.server.ServerConnection#start()
	 */
	@Override
	public void start() {
		thread = new Thread(this, "RMI_SERVER");
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.server.ServerConnection#close()
	 */
	@Override
	public void close() {
		if (registry != null) {
			try {
				registry.unbind(Connection.REMOTE_SERVER_RMI);
			} catch (NotBoundException | RemoteException e) {
				Application.exception(e);
				Application.exitError();
			}
		}
		if (thread != null) {
			thread.interrupt();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			registry = LocateRegistry.createRegistry(Connection.SERVER_REGISTRY_PORT);
			registry.bind(Connection.REMOTE_SERVER_RMI, this);
		} catch (RemoteException | AlreadyBoundException e) {
			Application.exception(e);
			Application.exitError();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer#registerOnServer
	 * (java.lang.String, java.lang.String, int)
	 */
	@Override
	public boolean registerOnServer(String username, String address, int port) throws RemoteException {
		RemoteClient client;
		try {
			Registry clientRegistry = LocateRegistry.getRegistry(address, port);
			client = (RemoteClient) clientRegistry.lookup(Connection.REMOTE_CLIENT_RMI);
		} catch (RemoteException | NotBoundException e) {
			Application.exception(e);
			return false;
		}
		return registerClientOnServer(username, client);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.server.ServerConnection#
	 * registerClientOnServer(java.lang.String,
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection)
	 */
	@Override
	public boolean registerClientOnServer(String username, ClientConnection client) {
		User newUser = Server.getInstance().registerClientOnServer(username, client);
		if (newUser == null) {
			return false;
		}
		try {
			registry.bind(username, newUser);
		} catch (RemoteException e) {
			Application.exception(e);
			return false;
		} catch (AlreadyBoundException e) {
			Application.exception(e, "", false);
			return true;
		}
		newUser.sendMessage("You are connected with \"" + newUser.getName() + "\" name");
		Application.println("Client \"" + username + "\" connected");
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.rmi.server.RemoteObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.rmi.server.RemoteObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
