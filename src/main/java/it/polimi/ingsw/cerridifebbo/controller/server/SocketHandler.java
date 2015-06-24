package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
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

// TODO: Auto-generated Javadoc
/**
 * The Class SocketHandler.
 *
 * @author cerridifebbo
 */
public class SocketHandler extends Thread implements ClientConnection {

	/** The socket. */
	private Socket socket;

	/** The oos. */
	private ObjectOutputStream oos;

	/** The in. */
	private BufferedReader in;

	/** The user. */
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
			Application.exception(e);
			return;
		}
		listen();
		close();
	}

	/**
	 * Listen.
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
	 * Close.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void close() {
		try {
			in.close();
			oos.close();
			socket.close();
		} catch (IOException e) {
			Application.exception(e);
		}
		interrupt();
	}

	/**
	 * Register client on server.
	 *
	 * @param username
	 *            the username
	 * @param client
	 *            the client
	 */
	public void registerClientOnServer(String username, SocketHandler client) {
		User newUser = Server.getInstance().registerClientOnServer(username, client);
		if (newUser == null) {
			try {
				oos.writeBoolean(false);
				oos.flush();
			} catch (IOException e) {
				Application.exception(e);
			}
			return;
		}
		this.user = newUser;
		Application.println("Client \"" + username + "\" connected");
		try {
			oos.writeBoolean(true);
			oos.flush();
		} catch (IOException e) {
			Application.exception(e, "BOOOH");
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

	@Override
	public void sendGameInformation(MapRemote map, PlayerRemote player, int size) throws IOException {
		oos.writeObject(Command.build(Command.SEND, Command.GAME_INFORMATION));
		oos.flush();
		List<Object> info = new ArrayList<Object>();
		info.add(map);
		info.add(player);
		info.add(size);
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
	 * Put move.
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
	 * The Class CommandHandler.
	 */
	public static class CommandHandler extends Command {

		/**
		 * Handle command.
		 *
		 * @param sh
		 *            the sh
		 * @param line
		 *            the line
		 */
		public static void handleCommand(SocketHandler sh, String line) {
			java.util.Map<String, String> params = translateCommand(line);
			String action = params.get(ACTION);
			switch (action) {
			case REGISTER:
				String username = params.get(DATA);
				sh.registerClientOnServer(username, sh);
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
