package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Util;
import it.polimi.ingsw.cerridifebbo.controller.common.Command;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class SocketInterface. Chosen by user, this connection listens for
 * incoming string commands from server.
 *
 * @author cerridifebbo
 */
public class SocketInterface implements NetworkInterface {

	/** The socket. */
	private Socket socket;

	/** The out stream. */
	private PrintWriter out;

	/** The input stream. */
	private ObjectInputStream ois;

	/** The username. */
	private String username;

	/** The graphic. */
	private Graphics graphics;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#connect()
	 */
	@Override
	public void connect() {
		try {
			socket = new Socket(Connection.SERVER_SOCKET_ADDRESS, Connection.SERVER_SOCKET_PORT);
		} catch (IOException e) {
			Util.exit(e, "Server not found");
		}
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			Util.exception(e);
			try {
				socket.close();
			} catch (IOException e1) {
				Util.exception(e1);
			}
			return;
		}
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			Util.exception(e);
			out.close();
			try {
				socket.close();
			} catch (IOException e1) {
				Util.exception(e1);
			}
			return;
		}
		do {
			username = Client.chooseUsername();
		} while (!registerClientOnServer());
		listen();
		close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#close()
	 */
	@Override
	public void close() {
		try {
			ois.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			Util.exit(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * registerClientOnServer()
	 */
	@Override
	public boolean registerClientOnServer() {
		out.println(Command.build(Command.REGISTER, username));
		try {
			return ois.readBoolean();
		} catch (IOException e) {
			Util.exception(e, "Unable to contact server");
			return false;
		}
	}

	/**
	 * Listen.
	 */
	private void listen() {
		while (true) {
			try {
				String line = (String) ois.readObject();
				CommandHandler.handleCommand(this, line);
			} catch (IOException | ClassNotFoundException e) {
				Util.exception(e, "Socket closed");
				break;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * setGraphicInterface
	 * (it.polimi.ingsw.cerridifebbo.controller.client.Graphics)
	 */
	@Override
	public void setGraphicInterface(Graphics graphics) {
		this.graphics = graphics;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#sendToServer
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void sendToServer(String action, String target) {
		String command = Command.build(Command.MOVE, action, target);
		out.println(command);
	}

	/**
	 * Starts turn.
	 */
	public void startTurn() {
		if (graphics.isInitialized()) {
			graphics.startTurn();
		}
	}

	/**
	 * Ends turn.
	 */
	public void endTurn() {
		if (graphics.isInitialized()) {
			graphics.endTurn();
		}
	}

	/**
	 * Asks user for move.
	 */
	public void askForMove() {
		if (graphics.isInitialized()) {
			graphics.declareMove();
		}
	}

	/**
	 * Asks user for sector.
	 */
	public void askForSector() {
		if (graphics.isInitialized()) {
			graphics.declareSector();
		}
	}

	/**
	 * Asks user for card.
	 */
	public void askForCard() {
		if (graphics.isInitialized()) {
			graphics.declareCard();
		}
	}

	/**
	 * Shows message from server.
	 *
	 * @param message
	 *            the message
	 */
	public void showMessage(String message) {
		if (graphics.isInitialized()) {
			graphics.sendMessage(message);
		} else {
			Util.println("SERVER) " + message);
		}
	}

	/**
	 * Receives game information from server.
	 */
	@SuppressWarnings("unchecked")
	private void receiveGameInformation() {
		try {
			List<Object> info = (List<Object>) ois.readObject();
			MapRemote map = (MapRemote) info.get(0);
			PlayerRemote player = (PlayerRemote) info.get(1);
			int numberOfPlayers = (Integer) info.get(2);
			setGameInformation(map, player, numberOfPlayers);
		} catch (IOException | ClassNotFoundException e) {
			Util.exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * setGameInformation
	 * (it.polimi.ingsw.cerridifebbo.controller.common.MapRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote, int)
	 */
	@Override
	public void setGameInformation(MapRemote map, PlayerRemote player, int size) {
		graphics.initialize(map, player, size);
	}

	/**
	 * Receive update from server.
	 */
	@SuppressWarnings("unchecked")
	private void receiveUpdate() {
		try {
			Object obj = ois.readObject();
			List<Object> update = (ArrayList<Object>) obj;
			PlayerRemote player = (PlayerRemote) update.get(0);
			ItemCardRemote card = (ItemCardRemote) update.get(1);
			boolean added = (Boolean) update.get(2);
			setPlayerUpdate(player, card, added);
		} catch (IOException | ClassNotFoundException e) {
			Util.exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * setPlayerUpdate
	 * (it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote, boolean)
	 */
	@Override
	public void setPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added) {
		graphics.updatePlayerPosition(player);
		if (card == null) {
			return;
		}
		if (added) {
			graphics.addPlayerCard(player, card);
		} else {
			graphics.deletePlayerCard(player, card);
		}

	}

	/**
	 * Receives hatch update from server.
	 */
	@SuppressWarnings("unchecked")
	public void receiveHatchUpdate() {
		try {
			Object obj = ois.readObject();
			List<Object> update = (ArrayList<Object>) obj;
			MapRemote map = (MapRemote) update.get(0);
			SectorRemote sector = (SectorRemote) update.get(1);
			setHatchUpdate(map, sector);
		} catch (IOException | ClassNotFoundException e) {
			Util.exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.polimi.ingsw.cerridifebbo.controller.client.NetworkInterface#
	 * setHatchUpdate(it.polimi.ingsw.cerridifebbo.controller.common.MapRemote,
	 * it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote)
	 */
	@Override
	public void setHatchUpdate(MapRemote map, SectorRemote sector) {
		if (graphics.isInitialized()) {
			graphics.updateEscapeHatch(map, sector);
		}
	}

	/**
	 * Disconnects client from server.
	 */
	public void disconnect() {
		showMessage("You are disconnected from the server! Hope you like the game! :)");
		close();
	}

	/**
	 * The Class CommandHandler.It manages String commands from client.
	 *
	 * @author cerridifebbo
	 */
	private static class CommandHandler extends Command {

		/**
		 * Handle command.
		 *
		 * @param si
		 *            the socket interface
		 * @param line
		 *            the String command
		 */
		public static void handleCommand(SocketInterface si, String line) {
			java.util.Map<String, String> params = translateCommand(line);
			String action = params.get(ACTION);
			switch (action) {
			case MESSAGE:
				si.showMessage(params.get(DATA));
				break;
			case SEND:
				receiveObject(si, params.get(DATA));
				break;
			case MOVE:
				si.askForMove();
				break;
			case SECTOR:
				si.askForSector();
				break;
			case CARD:
				si.askForCard();
				break;
			case START_TURN:
				si.startTurn();
				break;
			case END_TURN:
				si.endTurn();
				break;
			case DISCONNECT:
				si.disconnect();
				break;
			default:
				break;
			}
		}

		/**
		 * Receives object.
		 *
		 * @param si
		 *            the socket interface
		 * @param data
		 *            the type of data
		 */
		private static void receiveObject(SocketInterface si, String data) {
			switch (data) {
			case GAME_INFORMATION:
				si.receiveGameInformation();
				break;
			case UPDATE:
				si.receiveUpdate();
				break;
			case HATCH:
				si.receiveHatchUpdate();
				break;
			default:
				break;
			}
		}
	}
}
