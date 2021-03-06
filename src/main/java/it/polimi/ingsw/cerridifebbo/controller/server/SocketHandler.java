package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Util;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.Command;
import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class SocketHandler. It ensures the communication between server and
 * client on socket.
 *
 * @author cerridifebbo
 */
public class SocketHandler extends Thread implements ClientConnection {

	/** The socket. */
	private Socket socket;

	/** The output stream. */
	private ObjectOutputStream oos;

	/** The input stream. */
	private BufferedReader in;

	/** The user associated with client. */
	private User user;

	/**
	 * Instantiates a new socket handler.
	 *
	 * @param socket
	 *            the socket
	 */
	public SocketHandler(Socket socket) {
		super();
		this.socket = socket;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			Util.exception(e);
			return;
		}
		listen();
		close();
	}

	/**
	 * It listens for incoming commands from client.
	 */
	private void listen() {
		String input = null;
		boolean suspended = false;
		while (!suspended) {
			try {
				input = in.readLine();
			} catch (IOException e) {
				user.suspend(e);
				suspended = true;
				input = null;
			}
			if (input == null) {
				continue;
			}
			CommandHandler.handleCommand(this, input);
		}
	}

	/**
	 * Closes all the streams and thread associated.
	 */
	public void close() {
		try {
			in.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			Util.exception(e);
		}
		interrupt();
	}

	/**
	 * Registers client on server.
	 *
	 * @param username
	 *            the username
	 * 
	 */
	public void registerClientOnServer(String username) {
		User newUser = Server.getInstance().registerClientOnServer(username, this);
		if (newUser == null) {
			try {
				oos.writeBoolean(false);
				oos.flush();
			} catch (IOException e) {
				Util.exception(e);
			}
			return;
		}
		this.user = newUser;
		Util.println("Client \"" + username + "\" connected");
		try {
			oos.writeBoolean(true);
			oos.flush();
		} catch (IOException e) {
			Util.exception(e, "Socket closed on registering");
		}
		newUser.sendMessage("You are connected with \"" + newUser.getName() + "\" name");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#sendMessage
	 * (java.lang.String)
	 */
	@Override
	public void sendMessage(String string) throws IOException {
		oos.writeObject(Command.build(Command.MESSAGE, string));
		oos.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#
	 * sendGameInformation
	 * (it.polimi.ingsw.cerridifebbo.controller.common.MapRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote, int)
	 */
	@Override
	public void sendGameInformation(MapRemote map, PlayerRemote player, int numberOfPlayers) throws IOException {
		oos.writeObject(Command.build(Command.SEND, Command.GAME_INFORMATION));
		oos.flush();
		List<Object> info = new ArrayList<Object>();
		info.add(map);
		info.add(player);
		info.add(numberOfPlayers);
		oos.writeObject((ArrayList<Object>) info);
		oos.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#updatePlayer
	 * (it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote)
	 */
	@Override
	public void sendPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added) throws IOException {
		oos.writeObject(Command.build(Command.SEND, Command.UPDATE));
		oos.flush();
		List<Object> update = new ArrayList<Object>();
		update.add(player);
		update.add(card);
		update.add(added);
		oos.writeObject((ArrayList<Object>) update);
		oos.flush();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#
	 * sendHatchUpdate(it.polimi.ingsw.cerridifebbo.controller.common.MapRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote)
	 */
	@Override
	public void sendHatchUpdate(MapRemote map, SectorRemote sector) throws IOException {
		oos.writeObject(Command.build(Command.SEND, Command.HATCH));
		oos.flush();
		List<Object> update = new ArrayList<Object>();
		update.add(map);
		update.add(sector);
		oos.writeObject((ArrayList<Object>) update);
		oos.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForMove
	 * ()
	 */
	@Override
	public void askForMove() throws IOException {
		oos.writeObject(Command.build(Command.MOVE, null));
		oos.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForSector
	 * ()
	 */
	@Override
	public void askForSector() throws IOException {
		oos.writeObject(Command.build(Command.SECTOR, null));
		oos.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForCard
	 * ()
	 */
	@Override
	public void askForCard() throws IOException {
		oos.writeObject(Command.build(Command.CARD, null));
		oos.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#startTurn
	 * ()
	 */
	@Override
	public void startTurn() throws IOException {
		oos.writeObject(Command.build(Command.START_TURN, null));
		oos.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#endTurn()
	 */
	@Override
	public void endTurn() throws IOException {
		oos.writeObject(Command.build(Command.END_TURN, null));
		oos.flush();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#disconnect
	 * ()
	 */
	@Override
	public void disconnect() throws IOException {
		oos.writeObject(Command.build(Command.DISCONNECT, null));
		oos.flush();
		close();
	}

	/**
	 * Puts the move from client in the user.
	 *
	 * @param action
	 *            the action
	 * @param target
	 *            the target
	 */
	public void putMove(String action, String target) {
		user.putMove(action, target);
	}

	/**
	 * The Class CommandHandler. It manages String commands from client.
	 *
	 * @author cerridifebbo
	 */
	private static class CommandHandler extends Command {

		/**
		 * Handle the command.
		 *
		 * @param sh
		 *            the socket handler
		 * @param line
		 *            the line from client
		 */
		public static void handleCommand(SocketHandler sh, String line) {
			java.util.Map<String, String> params = translateCommand(line);
			String action = params.get(ACTION);
			switch (action) {
			case REGISTER:
				String username = params.get(DATA);
				sh.registerClientOnServer(username);
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
