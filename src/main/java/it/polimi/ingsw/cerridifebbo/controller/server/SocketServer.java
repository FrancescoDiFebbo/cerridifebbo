package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements ServerConnection {

	private static SocketServer instance;
	private int port;
	private ServerSocket serverSocket;
	private Thread thread;
	private boolean listening;

	public SocketServer() {
		this(Connection.SERVER_SOCKET_PORT);
	}

	public SocketServer(int port) {
		this.port = port;
		this.listening = false;
	}

	public static SocketServer getInstance() {
		if (instance == null) {
			return new SocketServer();
		}
		return instance;
	}

	@Override
	public void start() {
		thread = new Thread(this, "SOCKET_SERVER");
		thread.start();
	}

	@Override
	public void close() {
		endListening();
		if (thread != null) {
			thread.interrupt();
		}

	}

	@Override
	public void run() {
		try {
			startListening();
		} catch (IOException e) {
			Application.exception(e);
		}
	}

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

	@Override
	public boolean registerClientOnServer(String username, ClientConnection client) {
		return false;
	}
}
