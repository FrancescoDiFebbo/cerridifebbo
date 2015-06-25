package it.polimi.ingsw.cerridifebbo.controller.client;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A factory for creating NetworkInterface objects.
 * 
 * @author cerridifebbo
 */
public class NetworkInterfaceFactory {

	/** The Constant LOG. */
	private static final Logger LOG = Logger.getLogger(NetworkInterfaceFactory.class.getName());

	/** The Constant SOCKET_INTERFACE. */
	public static final String SOCKET_INTERFACE = "socket_interface";

	/** The Constant RMI_INTERFACE. */
	public static final String RMI_INTERFACE = "rmi_interface";

	/**
	 * Instantiates a new network interface factory.
	 */
	private NetworkInterfaceFactory() {

	}

	/**
	 * Gets the interface.
	 *
	 * @param param
	 *            the user choice.
	 * @return the interface
	 */
	public static NetworkInterface getInterface(String param) {
		switch (param) {
		case SOCKET_INTERFACE:
			return new SocketInterface();
		case RMI_INTERFACE:
			return createRMIInterface();
		default:
			return null;
		}
	}

	/**
	 * Creates a new NetworkInterface object.
	 *
	 * @return the network interface
	 */
	private static NetworkInterface createRMIInterface() {
		try {
			return new RMIInterface();
		} catch (RemoteException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
	}
}
