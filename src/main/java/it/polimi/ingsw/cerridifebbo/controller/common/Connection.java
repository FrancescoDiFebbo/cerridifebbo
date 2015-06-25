package it.polimi.ingsw.cerridifebbo.controller.common;

import java.net.InetAddress;

/**
 * The Class Connection provides connections properties.
 *
 * @author cerridifebbo
 */
public class Connection {

	/** The Constant SERVER_REGISTRY_PORT. */
	public static final int SERVER_REGISTRY_PORT = 2020;

	/** The Constant SERVER_SOCKET_PORT. */
	public static final int SERVER_SOCKET_PORT = 8888;

	/** The Constant SERVER_SOCKET_ADDRESS. */
	public static final String SERVER_SOCKET_ADDRESS = InetAddress.getLoopbackAddress().getHostAddress();

	/** The Constant REMOTE_CLIENT_RMI. */
	public static final String REMOTE_CLIENT_RMI = "it.polimi.ingsw.cerridifebbo.remote_client";

	/** The Constant REMOTE_SERVER_RMI. */
	public static final String REMOTE_SERVER_RMI = "it.polimi.ingsw.cerridifebbo.remote_server";

	/**
	 * Instantiates a new connection object.
	 */
	private Connection() {

	}
}
