package it.polimi.ingsw.cerridifebbo.controller.client;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NetworkInterfaceFactory {
	private static final Logger LOG = Logger.getLogger(NetworkInterfaceFactory.class.getName());
	public static final String SOCKET_INTERFACE = "socket_interface";
	public static final String RMI_INTERFACE = "rmi_interface";

	private NetworkInterfaceFactory() {

	}

	/**
	 * @param param
	 * @return
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

	private static NetworkInterface createRMIInterface() {
		try {
			return new RMIInterface();
		} catch (RemoteException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
			return null;
		}
	}
}
