package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.*;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.UUID;

public class RMIInterface implements NetworkInterface {

	private RemoteServer server;
	private int port;
	private UUID id = UUID.randomUUID();
	
	public void connect() throws RemoteException {
		Registry registry;
		while (true) {
			try {
				Random random = new Random();
				port = random.nextInt(65535);
				registry = LocateRegistry.createRegistry(port);
				break;
			} catch (RemoteException e) {
				System.err.println(port + " not available");
			}
		}
		RemoteClient client = new ClientImpl(id, port);
		try {
			registry.bind(RemoteClient.RMI_ID, client);
			System.out.println("Client bound at port " + port);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Client not bound.\nClosing client...");
			System.exit(-1);
		}
		try {
			registry = LocateRegistry.getRegistry(Connection.SERVER_REGISTRY_PORT);
		} catch (RemoteException e) {			
			e.printStackTrace();
			return;
		}
        try {
			server = (RemoteServer) registry.lookup(RemoteServer.RMI_ID);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
			return;
		}
        registerClientOnServer();
	}

	public void close() {
		//UNBINDA ROBA
	}

	public boolean registerClientOnServer() {
		try {
			return server.registerClientOnServer(id, port);
		} catch (RemoteException e) {
			return false;
		}		
	}
}
