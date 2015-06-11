package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;

import java.io.IOException;
import java.rmi.NotBoundException;

public class Client implements Runnable {
	private static final String NETWORK_INTERFACE_SELECTION = "Select '1' for RMI interface, '2' for socket interface";
	private static final String GRAPHICS_SELECTION = "Select '1' for GUI graphics, '2' for cli graphics";
	public static final String USERNAME_SELECTION = "Select a username";
	private static String CHOICE_ONE = "1";
	private static String CHOICE_TWO = "2";

	public static void main(String[] args) throws IOException, NotBoundException {
		new Client().start();
	}

	public void start() {
		new Thread(this).start();
	}

	@Override
	public void run() {
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
				line = Application.readLine(NETWORK_INTERFACE_SELECTION);
			} catch (IOException e) {
				Application.log(e);
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
				line = Application.readLine(GRAPHICS_SELECTION);
			} catch (IOException e) {
				Application.log(e);
				continue;
			}
			if (CHOICE_ONE.equals(line)) {
				return GraphicsFactory.getInterface(GraphicsFactory.GUI_INTERFACE);
			} else if (CHOICE_TWO.equals(line)) {
				return GraphicsFactory.getInterface(GraphicsFactory.CLI_INTERFACE);
			}
		}
	}

	public static String chooseUsername() {
		String name = null;
		do {
			try {
				name = Application.readLine(USERNAME_SELECTION);
			} catch (IOException e) {
				Application.exception(e);
				name = null;
			}
		} while (name == null || "".equals(name));
		return name.trim();
	}
}
