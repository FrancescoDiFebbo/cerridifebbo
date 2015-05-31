package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIInterface implements NetworkInterface {
	private static final Logger LOG = Logger.getLogger(RMIInterface.class.getName());

	private RemoteServer server;
	private int port;
	private UUID id = UUID.randomUUID();
	
	@Override
	public void connect() throws RemoteException {
		Registry registry;
		while (true) {
			try {
				Random random = new Random();
				port = random.nextInt(65535);
				registry = LocateRegistry.createRegistry(port);
				break;
			} catch (RemoteException e) {
				LOG.log(Level.WARNING, port + " not available", e);
			}
		}
		RemoteClient client = new ClientImpl(id);
		try {
			registry.bind(RemoteClient.RMI_ID, client);
			Application.println("Client bound at port " + port);
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
        registerClientOnServer();
	}

	@Override
	public void close() {
		//UNBINDA ROBA
	}

	@Override
	public boolean registerClientOnServer() {
		try {
			return server.registerClientOnServer(id, port);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}		
	}

	@Override
	public void sendToServer(String move) {
		// TODO Auto-generated method stub
		
	}
}
