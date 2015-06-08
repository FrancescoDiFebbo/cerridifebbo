package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
	private static final Logger LOG = Logger.getLogger(Client.class.getName());

	private static String NETWORK_INTERFACE_SELECTION = "Select '1' for RMI interface, '2' for socket interface";
	private static String GRAPHICS_SELECTION = "Select '1' for GUI graphics, '2' for cli graphics";
	private static String CHOICE_ONE = "1";
	private static String CHOICE_TWO = "2";

	public static void main(String[] args) throws IOException, NotBoundException {
		new Client().start();
	}

	public void start() {
		NetworkInterface network = chooseNetwork();
		if (network == null) {
			return;
		}
		Graphics graphic = chooseGraphic();
		if (graphic == null) {
			return;
		}
		graphic.setNetworkInterface(network);
		network.setGraphicInterface(graphic);
		network.connect();

	}

	private NetworkInterface chooseNetwork() {
		while (true) {
			String line = null;
			try {
				line = readLine(NETWORK_INTERFACE_SELECTION);
			} catch (IOException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
				continue;
			}
			if (CHOICE_ONE.equals(line)) {
				return NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.RMI_INTERFACE);
			} else if (CHOICE_TWO.equals(line)) {
				return NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.SOCKET_INTERFACE);
			}
		}
	}

	private Graphics chooseGraphic() {
		while (true) {
			String line = null;
			try {
				line = readLine(GRAPHICS_SELECTION);
			} catch (IOException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
				continue;
			}
			if (CHOICE_ONE.equals(line)) {
				return GraphicsFactory.getInterface(GraphicsFactory.GUI_INTERFACE);
			} else if (CHOICE_TWO.equals(line)) {
				return GraphicsFactory.getInterface(GraphicsFactory.CLI_INTERFACE);
			}
		}
	}

	private static String readLine(String format, Object... args) throws IOException {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		Application.println(String.format(format, args));

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		return br.readLine();
	}
}
