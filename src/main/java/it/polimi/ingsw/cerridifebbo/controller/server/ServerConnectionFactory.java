package it.polimi.ingsw.cerridifebbo.controller.server;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConnectionFactory {
	private static final Logger LOG = Logger.getLogger(ServerConnectionFactory.class.getName());
	public static final String SOCKET = "socket_server";
	public static final String RMI = "rmi_server";

	private ServerConnectionFactory() {

	}

	public static ServerConnection getConnection(Server server, String connection) {
		switch (connection) {
		case SOCKET:
			return new SocketServer(server);
		case RMI:
			return createRMIInterface(server);
		default:
			return null;
		}
	}

	private static ServerConnection createRMIInterface(Server server) {
		try {
			return new RMIServer(server);
		} catch (RemoteException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}
}
