package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.model.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer extends ServerConnection implements Runnable {
	private static final Logger LOG = Logger.getLogger(SocketServer.class.getName());
	private static final String IP = InetAddress.getLoopbackAddress().getHostAddress();
	private static final int PORT = 8888;

	private int port;
	private String address;
	private ServerSocket serverSocket;
	private Thread thread;
	private boolean listening;
	private String status;
	private List<SocketHandler> handlers;
	private Map<UUID, SocketHandler> clients = new HashMap<UUID, SocketHandler>();

	public SocketServer(Server server) {
		this(server, PORT, IP);
	}

	public SocketServer(Server server, int port, String address) {
		super(server);
		this.port = port;
		this.address = address;
		this.listening = false;
		this.status = "created";
		handlers = new LinkedList<SocketHandler>();
	}

	@Override
	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void close() throws IOException {
		if (thread != null) {
			thread.interrupt();
		}
		endListening();

	}

	@Override
	public void run() {
		try {
			startListening();
		} catch (IOException e) {
			this.status = "error";
			LOG.log(Level.SEVERE, this.status, e);
		}
	}

	private void startListening() throws IOException {
		if (!listening) {
			serverSocket = new ServerSocket(port);
			status = "listening";
			listening = true;
			while (listening) {
				try {
					Socket s = serverSocket.accept();
					SocketHandler sh = new SocketHandler(s, this);
					handlers.add(sh);
					sh.start();
				} catch (IOException e) {
					LOG.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
	}

	private void endListening() throws IOException {
		if (listening) {
			listening = false;
			for (SocketHandler sh : handlers)
				sh.close();
			serverSocket.close();
			status = "closed";
		}
	}

	@Override
	public boolean registerClientOnServer(UUID id, Object clientInterface) {
		SocketHandler handler = (SocketHandler) clientInterface;
		clients.put(id, handler);
		LOG.info("Client " + id + " connected on socket");
		server.registerClientOnServer(id, this);
		return true;
	}

	public String getStatus() {
		return status;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String getMoveFromUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String string, UUID selected) throws IOException {
		if (selected == null) {
			for (UUID id : clients.keySet()) {
				clients.get(id).sendMessage(string);
			}
		} else {
			clients.get(selected).sendMessage(string);
		}

	}

}
