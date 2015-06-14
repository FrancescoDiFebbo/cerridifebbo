package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// TODO: Auto-generated Javadoc
/**
 * The Class SocketServer.
 */
public class SocketServer implements ServerConnection {

	/** The instance. */
	private static SocketServer instance;
	
	/** The port. */
	private int port;
	
	/** The server socket. */
	private ServerSocket serverSocket;
	
	/** The thread. */
	private Thread thread;
	
	/** The listening. */
	private boolean listening;

	/**
	 * Instantiates a new socket server.
	 */
	public SocketServer() {
		this(Connection.SERVER_SOCKET_PORT);
	}

	/**
	 * Instantiates a new socket server.
	 *
	 * @param port the port
	 */
	public SocketServer(int port) {
		this.port = port;
		this.listening = false;
	}

	/**
	 * Gets the single instance of SocketServer.
	 *
	 * @return single instance of SocketServer
	 */
	public static SocketServer getInstance() {
		if (instance == null) {
			return new SocketServer();
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.cerridifebbo.controller.server.ServerConnection#start()
	 */
	@Override
	public void start() {
		thread = new Thread(this, "SOCKET_SERVER");
		thread.start();
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.cerridifebbo.controller.server.ServerConnection#close()
	 */
	@Override
	public void close() {
		endListening();
		if (thread != null) {
			thread.interrupt();
		}

	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			startListening();
		} catch (IOException e) {
			Application.exception(e);
		}
	}

	/**
	 * Start listening.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void startListening() throws IOException {
		if (!listening) {
			serverSocket = new ServerSocket(port);
			listening = true;
			while (listening) {
				try {
					Socket s = serverSocket.accept();
					SocketHandler sh = new SocketHandler(s);
					sh.start();
				} catch (IOException e) {
					Application.exception(e);
				}
			}
		}
	}

	/**
	 * End listening.
	 */
	private void endListening() {
		if (listening) {
			listening = false;
			try {
				serverSocket.close();
			} catch (IOException e) {
				Application.exception(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see it.polimi.ingsw.cerridifebbo.controller.server.ServerConnection#registerClientOnServer(java.lang.String, it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection)
	 */
	@Override
	public boolean registerClientOnServer(String username, ClientConnection client) {
		return false;
	}
}
