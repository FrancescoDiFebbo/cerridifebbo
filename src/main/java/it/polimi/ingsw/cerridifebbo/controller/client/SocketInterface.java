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

	@Override
	public boolean registerClientOnServer(UUID id, int port) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void sendMessage(UUID client, String message) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public void broadcastMessage(String message) throws RemoteException {
		// TODO Auto-generated method stub

	}

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
	}

	@Override
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}

}
