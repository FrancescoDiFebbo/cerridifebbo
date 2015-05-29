package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.UUID;

public class SocketInterface implements NetworkInterface {
	
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private UUID id = UUID.randomUUID();

	@Override
	public void connect() throws IOException{
		try {
			socket = new Socket(Connection.SERVER_SOCKET_ADDRESS, Connection.SERVER_SOCKET_PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {			
			e.printStackTrace();
			socket.close();
			return;
		}
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {			
			e.printStackTrace();
			out.close();
			socket.close();
			return;
		}
		registerClientOnServer();
	}

	@Override
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}
	
	public boolean registerClientOnServer() {
		out.println("action=register&id=" + id.toString());
		try {
			String line = in.readLine();
			System.out.println(line);
			return true;
		} catch (IOException e) {
			System.out.println("no");
			return false;
		}
	}

}
