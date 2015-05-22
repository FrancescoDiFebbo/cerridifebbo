package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.*;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Client {

	public static void main(String[] args) throws IOException, NotBoundException {
		new Client();
	}

	UUID id = UUID.randomUUID();
	int port;
	Registry registry;

	Client() throws RemoteException {
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
		try {
			RemoteClient client = new ClientImpl();
			registry.bind(RemoteClient.RMI_ID, client);
			System.out.println("Client bound at port " + port);
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Client not bound.\nClosing client...");
			System.exit(-1);
		}

		Scanner in = new Scanner(System.in);
		NetworkInterface network = NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.RMI_INTERFACE);
		network.connect();
		network.registerOnServer(id, port);
		while (true) {
			String line = in.nextLine();
			if (line.equals("q")) {
				in.close();
				break;
			}
			network.sendMessage(id, line);
		}
		network.close();
	}
}
