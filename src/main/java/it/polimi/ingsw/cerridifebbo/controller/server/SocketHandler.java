package it.polimi.ingsw.cerridifebbo.controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SocketHandler extends Thread {

	private Socket socket;
	private boolean stop;
	private PrintWriter out;
	private BufferedReader in;

	public SocketHandler(Socket socket) {
		super();
		this.setSocket(socket);
		this.stop = false;
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("Client connected on socket");
			String input, output;
			while ((input = in.readLine()) != null && !stop) {

				String[] splitted = input.split("&");
				Map<String, String> params = new HashMap<String, String>();
				for (String s : splitted) {
					params.put(s.split("=")[0], s.split("=")[1]);
				}
				output = CommandHandler.handleCommand(params);
				out.println(output);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		stop = true;
		in.close();
		out.close();
		socket.close();
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
