package it.polimi.ingsw.cerridifebbo.controller.server;

/**
 * A factory for creating server connections.
 * 
 * @author cerridifebbo
 */
public class ServerConnectionFactory {

	/** The Constant SOCKET. */
	public static final String SOCKET = "socket_server";

	/** The Constant RMI. */
	public static final String RMI = "rmi_server";

	/**
	 * Instantiates a new server connection factory.
	 */
	private ServerConnectionFactory() {

	}

	/**
	 * Gets the server able to listen on a type of connection.
	 *
	 * @param connection
	 *            the connection
	 * @return the server connection
	 */
	public static ServerConnection getConnection(String connection) {
		switch (connection) {
		case SOCKET:
			return new SocketServer();
		case RMI:
			return RMIServer.getInstance();
		default:
			return null;
		}
	}
}
