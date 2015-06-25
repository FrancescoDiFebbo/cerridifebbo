package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Util;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteClient;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;
import it.polimi.ingsw.cerridifebbo.controller.common.RemoteUser;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;

/**
 * The Class RMIInterface. Chosen by user, this connection bind itself on a
 * registry and can be easy-called by the methods provided by RemoteClient.
 *
 * @author cerridifebbo
 */
public class RMIInterface extends UnicastRemoteObject implements NetworkInterface, RemoteClient {

	private static final long serialVersionUID = 1L;

	/** The username. */
	private transient String username;

	/** The ip address. */
	private transient String ip;

	/** The port. */
	private transient int port;

	/** The server. */
	private transient RemoteServer server;

	/** The user bound on server registry. */
	private transient RemoteUser user;

	/** The graphic. */
	private transient Graphics graphics;

	/**
	 * Instantiates a new RMI interface.
	 *
	 * @throws RemoteException
	 *             the remote exception
	 */
	protected RMIInterface() throws RemoteException {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#connect()
	 */
	@Override
	public void connect() {
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			Util.exit(e);
		}
		Random random = new Random();
		Registry registry = null;
		while (true) {
			try {
				port = random.nextInt(65535);
				registry = LocateRegistry.createRegistry(port);
				break;
			} catch (RemoteException e) {
				Util.exception(e, port + " not available");
			}
		}
		try {
			registry.bind(Connection.REMOTE_CLIENT_RMI, this);
		} catch (RemoteException | AlreadyBoundException e) {
			Util.exit(e);
		}
		try {
			registry = LocateRegistry.getRegistry(Connection.SERVER_REGISTRY_PORT);
		} catch (RemoteException e) {
			Util.exit(e);
		}
		try {
			server = (RemoteServer) registry.lookup(Connection.REMOTE_SERVER_RMI);
		} catch (RemoteException | NotBoundException e) {
			Util.exit(e, "Server not found");
		}
		do {
			username = Client.chooseUsername();
		} while (!registerClientOnServer());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#close()
	 */
	@Override
	public void close() {
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			registry.unbind(Connection.REMOTE_CLIENT_RMI);
		} catch (RemoteException | NotBoundException e) {
			Util.exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * registerClientOnServer()
	 */
	@Override
	public boolean registerClientOnServer() {
		try {
			if (server.registerOnServer(username, ip, port)) {
				user = (RemoteUser) LocateRegistry.getRegistry(Connection.SERVER_REGISTRY_PORT).lookup(username);
				return true;
			} else {
				Util.println("Name already used");
				return false;
			}
		} catch (RemoteException | NotBoundException e) {
			Util.exception(e, "Unable to contact server");
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * setGraphicInterface
	 * (it.polimi.ingsw.cerridifebbo.controller.client.Graphics)
	 */
	@Override
	public void setGraphicInterface(Graphics graphics) {
		this.graphics = graphics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#sendMessage
	 * (java.lang.String)
	 */
	@Override
	public void sendMessage(String message) throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.sendMessage(message);
			return;
		}
		Util.println("SERVER) " + message);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#startTurn
	 * ()
	 */
	@Override
	public void startTurn() throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.startTurn();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#endTurn()
	 */
	@Override
	public void endTurn() throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.endTurn();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#
	 * sendGameInformation
	 * (it.polimi.ingsw.cerridifebbo.controller.common.MapRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote, int)
	 */
	@Override
	public void sendGameInformation(MapRemote map, PlayerRemote player, int numberOfPlayers) throws RemoteException {
		setGameInformation(map, player, numberOfPlayers);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * setGameInformation
	 * (it.polimi.ingsw.cerridifebbo.controller.common.MapRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote, int)
	 */
	@Override
	public void setGameInformation(MapRemote map, PlayerRemote player, int size) {
		graphics.initialize(map, player, size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#
	 * sendPlayerUpdate
	 * (it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote, boolean)
	 */
	@Override
	public void sendPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added) throws RemoteException {
		setPlayerUpdate(player, card, added);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * setPlayerUpdate
	 * (it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote, boolean)
	 */
	@Override
	public void setPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added) {
		graphics.updatePlayerPosition(player);
		if (card == null) {
			return;
		}
		if (added) {
			graphics.addPlayerCard(player, card);
		} else {
			graphics.deletePlayerCard(player, card);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#
	 * sendHatchUpdate(it.polimi.ingsw.cerridifebbo.controller.common.MapRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote)
	 */
	@Override
	public void sendHatchUpdate(MapRemote map, SectorRemote sector) throws RemoteException {
		setHatchUpdate(map, sector);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * setHatchUpdate(it.polimi.ingsw.cerridifebbo.controller.common.MapRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote)
	 */
	@Override
	public void setHatchUpdate(MapRemote map, SectorRemote sector) {
		if (graphics.isInitialized()) {
			graphics.updateEscapeHatch(map, sector);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForMove
	 * ()
	 */
	@Override
	public void askForMove() throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.declareMove();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForSector
	 * ()
	 */
	@Override
	public void askForSector() throws RemoteException {
		if (graphics.initialized) {
			graphics.declareSector();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForCard
	 * ()
	 */
	@Override
	public void askForCard() throws RemoteException {
		if (graphics.initialized) {
			graphics.declareCard();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#sendToServer
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void sendToServer(String action, String target) {
		try {
			user.sendMove(action, target);
		} catch (RemoteException e) {
			Util.exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#disconnect
	 * ()
	 */
	@Override
	public void disconnect() throws RemoteException {
		sendMessage("You are disconnected from the server! Hope you like the game! :)");
		close();
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
