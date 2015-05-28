package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.*;
import it.polimi.ingsw.cerridifebbo.view.cli.CLIGraphics;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

	private static String NETWORK_INTERFACE_SELECTION = "Select '1' for RMI interface, '2' for socket interface";
	private static String GRAPHICS_SELECTION = "Select '1' for GUI graphics, '2' for cli graphics";
	private static String CHOICE_ONE = "1";
	private static String CHOICE_TWO = "2";
	private static final Logger LOGGER = Logger.getLogger(CLIGraphics.class
			.getName());

	public static void main(String[] args) throws IOException,
			NotBoundException {
		new Client();
	}

	Client() throws RemoteException {
		Scanner in = new Scanner(System.in);
		NetworkInterface network;
		Graphics graphics;
		LOGGER.info(NETWORK_INTERFACE_SELECTION);
		LOGGER.log(Level.FINE, NETWORK_INTERFACE_SELECTION);
		while (true) {
			String line = in.next();
			if (line.equals(CHOICE_ONE)) {
				network = NetworkInterfaceFactory
						.getInterface(NetworkInterfaceFactory.RMI_INTERFACE);
				break;
			}
			if (line.equals(CHOICE_TWO)) {
				network = NetworkInterfaceFactory
						.getInterface(NetworkInterfaceFactory.SOCKET_INTERFACE);
				break;
			}
		}
		try {
			network.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info(GRAPHICS_SELECTION);
		while (true) {
			String line = in.nextLine();
			if (line.equals(CHOICE_ONE)) {
				graphics = GraphicsFactory
						.getInterface(GraphicsFactory.GUI_INTERFACE);
				break;
			}
			if (line.equals(CHOICE_TWO)) {
				graphics = GraphicsFactory
						.getInterface(GraphicsFactory.CLI_INTERFACE);
				break;
			}
		}
		graphics.setClient(this);
		in.close();
	}
}
