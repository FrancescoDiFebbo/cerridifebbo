package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
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

public class RMIInterface extends UnicastRemoteObject implements NetworkInterface, RemoteClient {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private transient String username;
	private transient String ip;
	private transient int port;
	private transient RemoteServer server;
	private transient RemoteUser user;
	private transient Graphics graphics;

	protected RMIInterface() throws RemoteException {
		super();
	}

	@Override
	public void connect() {
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			Application.exit(e);
		}
		Random random = new Random();
		Registry registry = null;
		while (true) {
			try {
				port = random.nextInt(65535);
				registry = LocateRegistry.createRegistry(port);
				break;
			} catch (RemoteException e) {
				Application.exception(e, port + " not available");
			}
		}
		try {
			registry.bind(Connection.REMOTE_CLIENT_RMI, this);
		} catch (RemoteException | AlreadyBoundException e) {
			Application.exit(e);
		}
		try {
			registry = LocateRegistry.getRegistry(Connection.SERVER_REGISTRY_PORT);
		} catch (RemoteException e) {
			Application.exit(e);
		}
		try {
			server = (RemoteServer) registry.lookup(Connection.REMOTE_SERVER_RMI);
		} catch (RemoteException | NotBoundException e) {
			Application.exit(e, "Server not found");
		}
		do {
			username = Client.chooseUsername();
		} while (!registerClientOnServer());
	}

	@Override
	public void close() {
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			registry.unbind(Connection.REMOTE_CLIENT_RMI);
		} catch (RemoteException | NotBoundException e) {
			Application.exception(e);
		}
	}

	@Override
	public boolean registerClientOnServer() {
		try {
			if (server.registerOnServer(username, ip, port)) {
				user = (RemoteUser) LocateRegistry.getRegistry(Connection.SERVER_REGISTRY_PORT).lookup(username);
				return true;
			} else {
				Application.println("Name already used");
				return false;
			}
		} catch (RemoteException | NotBoundException e) {
			Application.exception(e, "Unable to contact server");
			return false;
		}
	}

	@Override
	public void setGraphicInterface(Graphics graphics) {
		this.graphics = graphics;
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.sendMessage(message);
			return;
		}
		Application.println("SERVER) " + message);
	}

	@Override
	public void startTurn() throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.startTurn();
		}
	}

	@Override
	public void endTurn() throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.endTurn();
		}
	}

	@Override
	public void sendGameInformation(MapRemote map, PlayerRemote player, int size) throws RemoteException {
		setGameInformation(map, player, size);
	}

	@Override
	public void setGameInformation(MapRemote map, PlayerRemote player, int size) {
		graphics.initialize(map, player, size);
	}

	@Override
	public void sendPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added) throws RemoteException {
		setPlayerUpdate(player, card, added);
	}

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

	@Override
	public void sendHatchUpdate(MapRemote map, SectorRemote sector) throws RemoteException {
		setHatchUpdate(map, sector);
	}

	@Override
	public void setHatchUpdate(MapRemote map, SectorRemote sector) {
		if (graphics.isInitialized()) {
			graphics.updateEscapeHatch(map, sector);
		}
	}

	@Override
	public void askForMove() throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.declareMove();
		}
	}

	@Override
	public void askForSector() throws RemoteException {
		if (graphics.initialized) {
			graphics.declareSector();
		}
	}

	@Override
	public void askForCard() throws RemoteException {
		if (graphics.initialized) {
			graphics.declareCard();
		}
	}

	@Override
	public void sendToServer(String action, String target) {
		try {
			user.sendMove(action, target);
		} catch (RemoteException e) {
			Application.exception(e);
		}
	}

	@Override
	public void disconnect() throws RemoteException {
		sendMessage("You are disconnected from the server! Hope you like the game! :)");
		close();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
