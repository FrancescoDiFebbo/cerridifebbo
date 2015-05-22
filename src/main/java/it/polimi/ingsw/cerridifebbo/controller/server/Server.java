package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.*;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		new Server();
	}

	Server() {
		Registry registry = null;
		try {
			registry = LocateRegistry.createRegistry(Connection.SERVER_REGISTRY_PORT);
			RemoteServer server = new ServerImpl();
			registry.bind(RemoteServer.RMI_ID, server);
		} catch (RemoteException | AlreadyBoundException e) {
			e.printStackTrace();
			System.err.println("Server not bound. Closing server...");
			System.exit(-1);
		}

		System.out.println("Server bound\nServer is started...");

	}
}
