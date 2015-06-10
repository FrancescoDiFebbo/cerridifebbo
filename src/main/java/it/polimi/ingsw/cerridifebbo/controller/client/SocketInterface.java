package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.Command;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketInterface implements NetworkInterface {

	private Socket socket;
	private PrintWriter out;
	private ObjectInputStream ois;
	private String username;
	private Graphics graphics;

	@Override
	public void connect() {
		try {
			socket = new Socket(Connection.SERVER_SOCKET_ADDRESS, Connection.SERVER_SOCKET_PORT);
		} catch (IOException e) {
			Application.exception(e);
			return;
		}

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			Application.exception(e);
			try {
				socket.close();
			} catch (IOException e1) {
				Application.exception(e1);
			}
			return;
		}
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			Application.exception(e);
			out.close();
			try {
				socket.close();
			} catch (IOException e1) {
				Application.exception(e1);
			}
			return;
		}
		do {
			username = chooseUsername();
		} while (!registerClientOnServer());
		listen();
	}

	@Override
	public String chooseUsername() {
		return Client.chooseUsername();
	}

	@Override
	public void close() {
		try {
			ois.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			Application.exception(e);
			Application.exitError();
		}
	}

	@Override
	public boolean registerClientOnServer() {
		out.println(Command.build(Command.REGISTER, username));
		try {
			return ois.readBoolean();
		} catch (IOException e) {
			Application.exception(e, "Unable to contact server");
			return false;
		}
	}

	private void listen() {
		int readError = 0;
		while (readError < 2) {
			try {
				String line = (String) ois.readUnshared();
				CommandHandler.handleCommand(SocketInterface.this, line);
			} catch (IOException | ClassNotFoundException e) {
				Application.exception(e);
				readError++;
			}
		}
	}

	@Override
	public void setGraphicInterface(Graphics graphics) {
		this.graphics = graphics;
	}

	@Override
	public void sendToServer(String action, String target) {
		String command = CommandHandler.build(Command.MOVE, action, target);
		out.println(command);
	}

	public void startTurn() {
		if (graphics.isInitialized()) {
			graphics.startTurn();
		}

	}

	public void endTurn() {
		if (graphics.isInitialized()) {
			graphics.endTurn();
		}
	}

	public void askForMove() {
		if (graphics.isInitialized()) {
			graphics.declareMove();
		}
	}

	public void askForSector() {
		if (graphics.isInitialized()) {
			graphics.declareSector();
		}
	}

	public void askForCard() {
		if (graphics.isInitialized()) {
			graphics.declareCard();
		}
	}

	public void showMessage(String message) {
		if (graphics.isInitialized()) {
			graphics.sendMessage(message);
		} else {
			Application.println("SERVER) " + message);
		}
	}

	@SuppressWarnings("unchecked")
	private void receiveGameInformation() {
		try {
			List<Object> info = (List<Object>) ois.readUnshared();
			Map map = (Map) info.get(0);
			Player player = (Player) info.get(1);
			int numberOfPlayers = (Integer) info.get(2);
			setGameInformation(map, player, numberOfPlayers);
		} catch (IOException | ClassNotFoundException e) {
			Application.exception(e);
		}
	}

	@Override
	public void setGameInformation(Map map, Player player, int size) {
		graphics.initialize(map, player, size);
	}

	@SuppressWarnings("unchecked")
	private void receiveUpdate() {
		try {
			Object obj = ois.readUnshared();
			List<Object> update = (ArrayList<Object>) obj;
			Player player = (Player) update.get(0);
			Card card = (Card) update.get(1);
			boolean added = (Boolean) update.get(2);
			updatePlayer(player, card, added);
		} catch (IOException | ClassNotFoundException e) {
			Application.exception(e);
		}
	}

	@Override
	public void updatePlayer(Player player, Card card, boolean added) {
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

	private static class CommandHandler extends Command {

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
			default:
				break;
			}
		}

		private static void receiveObject(SocketInterface si, String data) {
			switch (data) {
			case GAME_INFORMATION:
				si.receiveGameInformation();
				break;
			case UPDATE:
				si.receiveUpdate();
				break;
			default:
				break;
			}
		}
	}
}
