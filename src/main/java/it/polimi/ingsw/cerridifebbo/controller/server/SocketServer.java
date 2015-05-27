package it.polimi.ingsw.cerridifebbo.controller.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SocketServer extends ServerConnection {

	private int port;
	private String address;
	private ServerSocket serverSocket;
	private boolean listening;
	private String status;
	private List<SocketHandler> handlers;

	public SocketServer(Server server) {
		this(server, 8888, "127.0.0.1");
	}

	public SocketServer(Server server, int port, String address) {
		super(server);
		this.setPort(port);
		this.setAddress(address);
		listening = false;
		setStatus("created");
		handlers = new LinkedList<SocketHandler>();
	}

	@Override
	public void start() {
		if (!listening) {
			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.err.println("Unable to start socket server");
				e.printStackTrace();
			}
			status = "listening";
			listening = true;
			while (listening) {
				try {
					Socket s = serverSocket.accept();
					SocketHandler sh = new SocketHandler(s);
					handlers.add(sh);
					sh.start();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void close() throws IOException {
		if (listening) {
			listening = false;
			for (SocketHandler sh : handlers)
				sh.close();
			serverSocket.close();
			status = "closed";
		}
	}

	@Override
	public boolean registerClientOnServer(UUID id, int port) {
		return true;
	}

	@Override
	public void run() {
		start();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

}
