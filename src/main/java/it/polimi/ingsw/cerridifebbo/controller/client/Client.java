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
	
	Client() throws RemoteException {
		Scanner in = new Scanner(System.in);
		NetworkInterface network;
		System.out.println("Select '1' for RMI interface, '2' for socket interface");
		while (true) {
			String line = in.nextLine();
			if (line.equals("1")) {
				network = NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.RMI_INTERFACE);
				break;
			}
			if (line.equals("2")) {
				network = NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.SOCKET_INTERFACE);
				break;
			}
		}
		try {
			network.connect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		while (true) {
			String line = in.nextLine();
			if (line.equals("q")) {
				in.close();
				break;
			}
		}
		try {
			network.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
