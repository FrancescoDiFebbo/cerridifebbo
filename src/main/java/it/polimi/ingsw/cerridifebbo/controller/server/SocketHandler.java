package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Command;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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

			String input;
			while ((input = in.readLine()) != null && !stop) {
				CommandHandler.handleCommand(this, input);
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
		out.println(Command.build(Command.MESSAGE, string));
	}

	public void sendGameInformation(int size, it.polimi.ingsw.cerridifebbo.model.Map map, User user) {
		String command = Command.build(Command.PLAYERS, String.valueOf(size));
		out.println(command);
		command = Command.build(Command.SEND, Command.GAME_INFORMATION);
		out.println(command);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			List<Object> info = new ArrayList<Object>();
			info.add(map);
			info.add(user.getPlayer());
			oos.writeObject((ArrayList<Object>)info);
			oos.close();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}	
	}

	public static class CommandHandler extends Command {
	
		public static void handleCommand(SocketHandler sh, String line) {
			Map<String, String> params = translateCommand(line);
			String action = params.get(ACTION);
			switch (action) {
			case REGISTER:
				UUID id = UUID.fromString(params.get(DATA));
				sh.getServer().registerClientOnServer(id, sh);
				break;
			default:
				break;
			}
		}
	
	}
}
