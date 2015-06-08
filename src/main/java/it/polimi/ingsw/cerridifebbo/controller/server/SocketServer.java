package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServer implements ServerConnection {
	private static final Logger LOG = Logger.getLogger(SocketServer.class.getName());
	private static final int PORT = 8888;

	private int port;
	private final Server server;
	private String address;
	private ServerSocket serverSocket;
	private Thread thread;
	private boolean listening;
	private String status;
	private Map<User, SocketHandler> clients = new HashMap<User, SocketHandler>();

	public SocketServer(Server server) {
		this(server, PORT, Connection.SERVER_SOCKET_ADDRESS);
	}

	public SocketServer(Server server, int port, String address) {
		this.server = server;
		this.port = port;
		this.address = address;
		this.listening = false;
		this.status = "created";
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
					sh.start();
				} catch (IOException e) {
					LOG.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		}
	}

	private void endListening() {
		if (listening) {
			listening = false;
			for (SocketHandler sh : clients.values()) {
				try {
					sh.close();
				} catch (IOException e) {
					LOG.log(Level.WARNING, e.getMessage(), e);
				}
			}
			try {
				serverSocket.close();
			} catch (IOException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
			status = "closed";
		}
	}

	@Override
	public boolean registerClientOnServer(UUID id, Object clientInterface) {
		User newUser = server.registerClientOnServer(id, this);
		SocketHandler handler = (SocketHandler) clientInterface;
		handler.setLinkedUser(newUser);
		handler.setName("HANDLER-" + id.toString().split("-")[0]);
		clients.put(newUser, handler);
		LOG.info("Client " + id + " connected on socket");		
		clients.get(newUser).sendMessage("You are connected to the server");
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
	public void sendMessage(User user, String string) {
		if (user == null) {
			for (User u : clients.keySet()) {
				clients.get(u).sendMessage(string);
			}
		} else {
			clients.get(user).sendMessage(string);
		}

	}

	@Override
	public void sendGameInformation(User user, int size, it.polimi.ingsw.cerridifebbo.model.Map map) {
		clients.get(user).sendGameInformation(size, map, user.getPlayer());

	}

	@Override
	public void askForMove(User user) {
		clients.get(user).askForMove();

	}

	@Override
	public void askForSector(User user) {
		clients.get(user).askForSector();

	}

	@Override
	public void updatePlayer(User user, Card card, boolean added) {
		clients.get(user).updatePlayer(user.getPlayer(), card, added);		
	}

	@Override
	public void startTurn(User user) {
		clients.get(user).starTurn();		
	}

	@Override
	public void endTurn(User user) {
		clients.get(user).endTurn();
		
	}

	@Override
	public void disconnectUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean poke(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
