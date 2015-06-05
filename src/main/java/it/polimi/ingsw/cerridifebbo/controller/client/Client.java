package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client implements Runnable {
	private static final Logger LOG = Logger.getLogger(Client.class.getName());

	private static String NETWORK_INTERFACE_SELECTION = "Select '1' for RMI interface, '2' for socket interface";
	private static String GRAPHICS_SELECTION = "Select '1' for GUI graphics, '2' for cli graphics";
	private static String CHOICE_ONE = "1";
	private static String CHOICE_TWO = "2";

	public static void main(String[] args) throws IOException, NotBoundException {
		new Client().run();
	}

	@Override
	public void run() {
		NetworkInterface network = null;
		boolean chosen = false;
		while (!chosen) {
			String line = null;
			try {
				line = readLine(NETWORK_INTERFACE_SELECTION);
			} catch (IOException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
				return;
			}
			if (CHOICE_ONE.equals(line)) {
				network = NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.RMI_INTERFACE);
				chosen = true;
			} else if (CHOICE_TWO.equals(line)) {
				network = NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.SOCKET_INTERFACE);
				chosen = true;
			}
		}

		Graphics graphic = null;
		chosen = false;
		while (!chosen) {
			String line = null;
			try {
				line = readLine(GRAPHICS_SELECTION);
			} catch (IOException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
				return;
			}
			if (CHOICE_ONE.equals(line)) {
				graphic = GraphicsFactory.getInterface(GraphicsFactory.GUI_INTERFACE);
				chosen = true;

			} else if (CHOICE_TWO.equals(line)) {
				graphic = GraphicsFactory.getInterface(GraphicsFactory.CLI_INTERFACE);

			}

			chosen = true;
		}
		if (network == null) {
			return;
		}
		graphic.setNetworkInterface(network);
		network.setGraphicInterface(graphic);
		network.connect();

	}

	private static String readLine(String format, Object... args) throws IOException {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		Application.println(String.format(format, args));

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String read = br.readLine();

		return read;
	}
}
