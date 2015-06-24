package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Util;

import java.io.IOException;
import java.util.Calendar;

/**
 * The Class Client. Allows the user to choose the type of connection
 * client-server and the graphic interface. This is the entry class for the
 * client application.
 * 
 * @author cerridifebbo
 */
public class Client implements Runnable {

	/** The Constant NETWORK_SELECTION. */
	private static final String NETWORK_SELECTION = "Select '1' for RMI interface, '2' for socket interface";

	/** The Constant GRAPHIC_SELECTION. */
	private static final String GRAPHIC_SELECTION = "Select '1' for GUI graphics, '2' for cli graphics";

	/** The Constant USERNAME_SELECTION. */
	private static final String USERNAME_SELECTION = "Select a username";

	/** The Constant CHOICE_ONE. */
	private static final String CHOICE_ONE = "1";

	/** The Constant CHOICE_TWO. */
	private static final String CHOICE_TWO = "2";

	/**
	 * The main method. It starts the client.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		new Client().start();
	}

	/**
	 * Starts the client within a thread.
	 */
	public void start() {
		new Thread(this, "CLIENT" + Calendar.getInstance().get(Calendar.MILLISECOND)).start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
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

	/**
	 * Allows the user to choose a connection interface.
	 *
	 * @return the network interface
	 */
	private NetworkInterface chooseNetwork() {
		while (true) {
			String line = null;
			try {
				line = Util.readLine(NETWORK_SELECTION);
			} catch (IOException e) {
				Util.exception(e);
				continue;
			}
			if (CHOICE_ONE.equals(line)) {
				return NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.RMI_INTERFACE);
			} else if (CHOICE_TWO.equals(line)) {
				return NetworkInterfaceFactory.getInterface(NetworkInterfaceFactory.SOCKET_INTERFACE);
			}
		}
	}

	/**
	 * Allows the user to choose a graphic interface.
	 *
	 * @return the graphics interface
	 */
	private Graphics chooseGraphic() {
		while (true) {
			String line = null;
			try {
				line = Util.readLine(GRAPHIC_SELECTION);
			} catch (IOException e) {
				Util.exception(e);
				continue;
			}
			if (CHOICE_ONE.equals(line)) {
				return GraphicsFactory.getInterface(GraphicsFactory.GUI_INTERFACE);
			} else if (CHOICE_TWO.equals(line)) {
				return GraphicsFactory.getInterface(GraphicsFactory.CLI_INTERFACE);
			}
		}
	}

	/**
	 * Allows the user to choose a name.
	 *
	 * @return the name
	 */
	public static String chooseUsername() {
		String name = null;
		do {
			try {
				name = Util.readLine(USERNAME_SELECTION).trim();
			} catch (IOException e) {
				Util.exception(e);
				name = null;
			}
		} while (name == null || "".equals(name));
		return name;
	}
}
