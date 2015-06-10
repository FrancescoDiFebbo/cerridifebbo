package it.polimi.ingsw.cerridifebbo.controller.common;

import java.net.InetAddress;

public class Connection {

	public static final int SERVER_REGISTRY_PORT = 2020;
	public static final int SERVER_SOCKET_PORT = 8888;
	public static final String SERVER_SOCKET_ADDRESS = InetAddress.getLoopbackAddress().getHostAddress();

	private Connection() {

	}
}
