package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.*;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIInterface extends UnicastRemoteObject implements NetworkInterface, RemoteClient {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(RMIInterface.class.getName());
	private static final int MAX_ATTEMPTS = 5;

	private transient RemoteServer server;
	private UUID id = UUID.randomUUID();
	private int port;
	private transient Graphics graphics;

	protected RMIInterface() throws RemoteException {
		super();
	}

	@Override
	public void connect() {
		Registry registry;
		Random random = new Random();
		while (true) {
			try {
				port = random.nextInt(65535);
				registry = LocateRegistry.createRegistry(port);
				break;
			} catch (RemoteException e) {
				LOG.log(Level.INFO, port + " not available", e);
			}
		}
		try {
			registry.bind(RemoteClient.RMI_ID, this);
		} catch (RemoteException | AlreadyBoundException e) {
			LOG.log(Level.SEVERE, "Client not bound.\nClosing client...", e);
			Application.exitError();
		}
		try {
			registry = LocateRegistry.getRegistry(Connection.SERVER_REGISTRY_PORT);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		try {
			server = (RemoteServer) registry.lookup(RemoteServer.RMI_ID);
		} catch (RemoteException | NotBoundException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		if (!registerClientOnServer()) {
			close();
		}
	}

	@Override
	public void close() {
		try {
			Registry registry = LocateRegistry.getRegistry(port);
			registry.unbind(RemoteClient.RMI_ID);
		} catch (RemoteException | NotBoundException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}
	}

	@Override
	public boolean registerClientOnServer() {
		try {
			return server.registerOnServer(id, port);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		showMessage(message);
	}

	public void showMessage(String message) {
		if (graphics.isInitialized()) {
			graphics.sendMessage(message);
			return;
		}
		Application.println("SERVER) " + message);
	}

	@Override
	public void setGraphicInterface(Graphics graphics) {
		this.graphics = graphics;

	}

	@Override
	public void sendGameInformation(int size, Map map, Player player) throws RemoteException {
		setGameInformation(map, player, size);
	}

	@Override
	public void setGameInformation(Map map, Player player, int size) {
		graphics.initialize(map, player, size);
	}

	@Override
	public void updatePlayer(Player player, Card card, boolean added) {
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
	public void askForMove() throws RemoteException {
		if (graphics.isInitialized()) {
			graphics.declareMove();
			return;
		}
	}

	@Override
	public void sendToServer(String action, String target) {
		int attempts = 0;
		while (attempts < MAX_ATTEMPTS) {
			try {
				server.sendMove(id, action, target);
				break;
			} catch (RemoteException e) {
				attempts++;
				LOG.log(Level.INFO, e.getMessage(), e);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
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
	public void disconnect() throws RemoteException {
		Application.println("You are disconnected from the server! Hope you like it! :)");
		close();
		Application.exitSuccess();
	}

	@Override
	public boolean poke() throws RemoteException {
		return true;
	}

	@Override
	public void askForSector() throws RemoteException {
		if (graphics.initialized) {
			graphics.declareSector();
		}		
	}

}
