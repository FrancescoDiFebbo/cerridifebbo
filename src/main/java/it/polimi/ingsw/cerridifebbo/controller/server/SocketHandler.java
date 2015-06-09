package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.Command;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
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
	private ObjectOutputStream oos;
	private BufferedReader in;
	private User linkedUser;

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
			oos = new ObjectOutputStream(socket.getOutputStream());
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
		oos.close();
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

	public void sendGameInformation(int size, it.polimi.ingsw.cerridifebbo.model.Map map, Player player) {
		String command = Command.build(Command.SEND, Command.GAME_INFORMATION);
		out.println(command);
		List<Object> info = new ArrayList<Object>();
		info.add(map);
		info.add(player);
		info.add(size);
		try {
			oos.writeUnshared((ArrayList<Object>) info);
			oos.flush();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	public void updatePlayer(Player player, Card card, boolean added) {
		String command = CommandHandler.build(Command.SEND, Command.UPDATE);
		out.println(command);
		List<Object> update = new ArrayList<Object>();
		update.add(player);
		update.add(card);
		update.add(added);
		try {
			oos.writeUnshared((ArrayList<Object>) update);
			oos.flush();
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}

	}

	public void askForMove() {
		String command = CommandHandler.build(Command.MOVE, null);
		out.println(command);
	}

	public void askForSector() {
		String command = CommandHandler.build(Command.SECTOR, null);
		out.println(command);
	}	

	public void starTurn() {
		String command = CommandHandler.build(Command.START_TURN, null);
		out.println(command);

	}

	public void endTurn() {
		String command = CommandHandler.build(Command.END_TURN, null);
		out.println(command);
	}

	public User getLinkedUser() {
		return linkedUser;
	}

	public void setLinkedUser(User linkedUser) {
		this.linkedUser = linkedUser;
	}
	
	public void putMove(String action, String target){
		linkedUser.putMove(new Move(action, target));
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
			case MOVE:
				sh.putMove(params.get(DATA), params.get(DATA + "0"));
				break;
			default:
				break;
			}
		}

	}
}
