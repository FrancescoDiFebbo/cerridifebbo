package it.polimi.ingsw.cerridifebbo.controller.server;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.ClientConnection;
import it.polimi.ingsw.cerridifebbo.controller.common.Command;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketHandler extends Thread implements ClientConnection {

	private Socket socket;
	private ObjectOutputStream oos;
	private BufferedReader in;
	private User user;

	public SocketHandler(Socket socket) {
		super();
		this.socket = socket;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() throws IOException {
		in.close();
		oos.close();
		socket.close();
	}

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
			Application.exception(e);
		}
		newUser.sendMessage("You are connected with \"" + newUser.getName() + "\" name");
	}

	@Override
	public void sendMessage(String string) {
		try {
			oos.writeUnshared(Command.build(Command.MESSAGE, string));
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}
	}

	@Override
	public void sendGameInformation(Map map, Player player, int size) {
		try {
			oos.writeUnshared(Command.build(Command.SEND, Command.GAME_INFORMATION));
			oos.flush();
		} catch (IOException e1) {
			Application.exception(e1);
		}
		List<Object> info = new ArrayList<Object>();
		info.add(map);
		info.add(player);
		info.add(size);
		try {
			oos.writeUnshared((ArrayList<Object>) info);
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}
	}

	@Override
	public void updatePlayer(Player player, Card card, boolean added) {
		try {
			oos.writeUnshared(CommandHandler.build(Command.SEND, Command.UPDATE));
			oos.flush();
		} catch (IOException e1) {
			Application.exception(e1);
		}
		List<Object> update = new ArrayList<Object>();
		update.add(player);
		update.add(card);
		update.add(added);
		try {
			oos.writeUnshared((ArrayList<Object>) update);
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}
	}

	@Override
	public void askForMove() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.MOVE, null));
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}
	}

	@Override
	public void askForSector() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.SECTOR, null));
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}

	}

	@Override
	public void askForCard() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.CARD, null));
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}

	}

	@Override
	public void startTurn() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.START_TURN, null));
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}

	}

	@Override
	public void endTurn() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.END_TURN, null));
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}
	}

	@Override
	public void disconnect() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.DISCONNECT, null));
			oos.flush();
			close();
		} catch (IOException e) {
			Application.exception(e);
		}
	}

	@Override
	public boolean poke() {
		try {
			oos.writeUnshared(CommandHandler.build(Command.POKE, null));
			oos.flush();
		} catch (IOException e) {
			Application.exception(e);
		}

		try {
			String input = in.readLine();
			return Boolean.parseBoolean(input);
		} catch (IOException e) {
			Application.exception(e);
			return false;
		}
	}

	public void putMove(String action, String target) {
		user.putMove(action, target);
	}

	public static class CommandHandler extends Command {

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
