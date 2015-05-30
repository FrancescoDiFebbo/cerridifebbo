package it.polimi.ingsw.cerridifebbo.controller.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketHandler extends Thread {
	private static final Logger LOG = Logger.getLogger(SocketHandler.class.getName());

	private Socket socket;
	private ServerConnection server;
	private boolean stop;
	private PrintWriter out;
	private BufferedReader in;

	public SocketHandler(Socket socket, ServerConnection server) {
		super();
		this.socket = socket;
		this.server = server;
		this.stop = false;
	}

	@Override
	public void run() {
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
			String input, output;
			while ((input = in.readLine()) != null && !stop) {

				String[] splitted = input.split("&");
				Map<String, String> params = new HashMap<String, String>();
				for (String s : splitted) {
					//TODO Gestire input errati
					params.put(s.split("=")[0], s.split("=")[1]);
				}
				output = CommandHandler.handleCommand(this, params);
				out.println(output);
			}
		} catch (IOException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}
	}

	public void close() throws IOException {
		stop = true;
		in.close();
		out.close();
		socket.close();
	}

	public ServerConnection getServer() {
		return server;
	}

	public Socket getSocket() {
		return socket;
	}

	public void sendMessage(String string) {
		out.println(string);		
	}
}
