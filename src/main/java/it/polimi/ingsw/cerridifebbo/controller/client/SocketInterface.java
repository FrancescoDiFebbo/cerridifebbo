package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketInterface implements NetworkInterface {
	private static final Logger LOG = Logger.getLogger(SocketInterface.class.getName());
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private UUID id = UUID.randomUUID();

	@Override
	public void connect() throws IOException{
		try {
			socket = new Socket(Connection.SERVER_SOCKET_ADDRESS, Connection.SERVER_SOCKET_PORT);
		} catch (UnknownHostException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return;
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return;
		}
		
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {			
			LOG.log(Level.SEVERE, e.getMessage(), e);
			socket.close();
			return;
		}
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {			
			LOG.log(Level.SEVERE, e.getMessage(), e);
			out.close();
			socket.close();
			return;
		}
		registerClientOnServer();
		listen();
	}

	@Override
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}
	
	@Override
	public boolean registerClientOnServer() {
		out.println("action=register&id=" + id.toString());
		try {
			String line = in.readLine();
			Application.println(line);
			return true;
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}
	
	private void listen(){
		while (true) {
			try {
				String line = in.readLine();
				Application.println(line);
			} catch (IOException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

}
