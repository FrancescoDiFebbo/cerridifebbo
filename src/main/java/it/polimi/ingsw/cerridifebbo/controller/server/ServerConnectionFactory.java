package it.polimi.ingsw.cerridifebbo.controller.server;

public class ServerConnectionFactory {
	public static final String SOCKET = "socket_server";
	public static final String RMI = "rmi_server";

	private ServerConnectionFactory() {

	}

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
