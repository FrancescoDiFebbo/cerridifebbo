package it.polimi.ingsw.cerridifebbo.controller.client;

import java.rmi.RemoteException;

public class NetworkInterfaceFactory {

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
			try {
				return new RMIInterface();
			} catch (RemoteException e) {
				return null;
			}
		default:
			return null;
		}
	}
}
