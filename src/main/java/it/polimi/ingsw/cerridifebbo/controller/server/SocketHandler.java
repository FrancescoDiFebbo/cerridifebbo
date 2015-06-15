package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.Command;
import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
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
			}
			if (input == null) {
				continue;
			}
			CommandHandler.handleCommand(this, input);
		}
		try {
			close();
		} catch (IOException e) {
			Application.exception(e, "Already closed", false);
		}
	}

	/**
	 * Close.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void close() throws IOException {
		in.close();
		oos.close();
		socket.close();
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
			Application.exception(e, "BOOOH", true);
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
	public void sendMessage(String string) {
		try {
			oos.writeUnshared(Command.build(Command.MESSAGE, string));
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#
	 * sendGameInformation(it.polimi.ingsw.cerridifebbo.model.Map,
	 * it.polimi.ingsw.cerridifebbo.model.Player, int)
	 */
	// @Override
	// public void sendGameInformation(Map map, Player player, int size) {
	// try {
	// oos.writeUnshared(Command.build(Command.SEND, Command.GAME_INFORMATION));
	// oos.flush();
	// } catch (IOException e1) {
	// user.suspend(e1);
	// return;
	// }
	// List<Object> info = new ArrayList<Object>();
	// info.add(map);
	// info.add(player);
	// info.add(size);
	// try {
	// oos.writeUnshared((ArrayList<Object>) info);
	// oos.flush();
	// } catch (IOException e) {
	// Application.exception(e);
	// }
	// }

	@Override
	public void sendGameInformation(MapRemote map, PlayerRemote player, int size) throws RemoteException {
		try {
			oos.writeObject(Command.build(Command.SEND, Command.GAME_INFORMATION));
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
			return;
		}
		List<Object> info = new ArrayList<Object>();
		info.add(map);
		info.add(player);
		info.add(size);
		try {
			oos.writeObject((ArrayList<Object>) info);
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#updatePlayer
	 * (it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote)
	 */
	@Override
	public void updatePlayer(PlayerRemote player, ItemCardRemote card, boolean added) throws RemoteException {
		try {
			oos.writeUnshared(CommandHandler.build(Command.SEND, Command.UPDATE));
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
		}
		List<Object> update = new ArrayList<Object>();
		update.add(player);
		update.add(card);
		update.add(added);
		try {
			oos.writeUnshared((ArrayList<Object>) update);
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForMove
	 * ()
	 */
	@Override
	public void askForMove() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.MOVE, null));
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForSector
	 * ()
	 */
	@Override
	public void askForSector() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.SECTOR, null));
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#askForCard
	 * ()
	 */
	@Override
	public void askForCard() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.CARD, null));
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#startTurn
	 * ()
	 */
	@Override
	public void startTurn() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.START_TURN, null));
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#endTurn()
	 */
	@Override
	public void endTurn() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.END_TURN, null));
			oos.flush();
		} catch (IOException e) {
			user.suspend(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection#disconnect
	 * ()
	 */
	@Override
	public void disconnect() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.DISCONNECT, null));
			oos.flush();
			close();
		} catch (IOException e) {
			Application.exception(e, "Already disconnected", false);
		}
	}

	// @Override
	// public boolean poke() {
	// try {
	// oos.writeUnshared(CommandHandler.build(Command.POKE, null));
	// oos.flush();
	// } catch (IOException e) {
	// user.suspend(e);
	// }
	// try {
	// String input = in.readLine();
	// return Boolean.parseBoolean(input);
	// } catch (IOException e) {
	// user.suspend(e);
	// return false;
	// }
	// }

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
