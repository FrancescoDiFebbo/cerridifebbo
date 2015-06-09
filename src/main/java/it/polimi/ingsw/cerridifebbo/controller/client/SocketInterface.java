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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketInterface implements NetworkInterface {
	private static final Logger LOG = Logger.getLogger(SocketInterface.class.getName());

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	ObjectInputStream ois;
	private UUID id = UUID.randomUUID();
	private Graphics graphics;

	@Override
	public void connect() {
		try {
			socket = new Socket(Connection.SERVER_SOCKET_ADDRESS, Connection.SERVER_SOCKET_PORT);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return;
		}

		try {
			out = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			try {
				socket.close();
			} catch (IOException e1) {
				LOG.log(Level.WARNING, e1.getMessage(), e1);
			}
			return;
		}
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			out.close();
			try {
				socket.close();
			} catch (IOException e1) {
				LOG.log(Level.WARNING, e1.getMessage(), e1);
			}
			return;
		}
		if (!registerClientOnServer()) {
			close();
		}
		listen();
	}

	@Override
	public void close() {
		try {
			in.close();
			ois.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
			Application.exitError();
		}
	}

	@Override
	public boolean registerClientOnServer() {
		out.println(Command.build(Command.REGISTER, id.toString()));
		try {
			String line = in.readLine();
			CommandHandler.handleCommand(SocketInterface.this, line);
			return true;
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			return false;
		}
	}

	private void listen() {
		int readError = 0;
		while (true) {
			try {
				String line = in.readLine();
				CommandHandler.handleCommand(SocketInterface.this, line);
			} catch (IOException e) {
				LOG.log(Level.SEVERE, e.getMessage(), e);
				readError++;
				if (readError > 2) {
					Application.exitError();
				}
			}
		}
	}

	@Override
	public void setGraphicInterface(Graphics graphics) {
		this.graphics = graphics;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectInputStream getOis() {
		return ois;
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
				receiveGameInformation(si);
				break;
			case UPDATE:
				receiveUpdate(si);
				break;
			default:
				break;
			}

		}

		@SuppressWarnings("unchecked")
		private static void receiveUpdate(SocketInterface si) {
			ObjectInputStream ois = si.getOis();
			try {
				Object obj = ois.readUnshared();
				Application.println(obj.toString());
				List<Object> update = (ArrayList<Object>) obj;
				Player player = (Player) update.get(0);
				Card card = (Card) update.get(1);
				boolean added = (Boolean) update.get(2);
				si.updatePlayer(player, card, added);
			} catch (IOException | ClassNotFoundException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}

		}

		@SuppressWarnings("unchecked")
		private static void receiveGameInformation(SocketInterface si) {
			ObjectInputStream ois = si.getOis();
			try {
				List<Object> info = (List<Object>) ois.readUnshared();
				Map map = (Map) info.get(0);
				Player player = (Player) info.get(1);
				int numberOfPlayers = (Integer) info.get(2);
				si.setGameInformation(map, player, numberOfPlayers);
			} catch (IOException | ClassNotFoundException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

	@Override
	public void sendToServer(String action, String target) {
		String command = CommandHandler.build(Command.MOVE, action, target);
		out.println(command);
	}

	public void endTurn() {
		if (graphics.isInitialized()) {
			graphics.endTurn();
		}		
	}

	public void startTurn() {
		if (graphics.isInitialized()) {
			graphics.startTurn();
		}
		
	}

	public void askForCard() {
		if (graphics.isInitialized()) {
			graphics.declareCard();
		}
	}

	public void askForSector() {
		if (graphics.isInitialized()) {
			graphics.declareSector();
		}
	}

	public void askForMove() {
		if (graphics.isInitialized()) {
			graphics.declareMove();
		}
	}

	public void showMessage(String message) {
		if (graphics.isInitialized()) {
			graphics.sendMessage(message);
		} else {
			Application.println("SERVER) " + message);
		}

	}

	public void updatePlayer(Player player, Card card, boolean added) {
		if (graphics.isInitialized()) {
			graphics.updatePlayerPosition(player);
			if (added) {
				graphics.addPlayerCard(player, card);
			} else {
				graphics.deletePlayerCard(player, card);
			}
		}
	}

	@Override
	public void setGameInformation(Map map, Player player, int size) {
		graphics.initialize(map, player, size);
	}
}
