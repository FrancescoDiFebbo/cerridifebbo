package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.controller.common.Command;
import it.polimi.ingsw.cerridifebbo.controller.common.Connection;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketInterface implements NetworkInterface {
	private static final Logger LOG = Logger.getLogger(SocketInterface.class.getName());

	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return;
		}
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			LOG.log(Level.SEVERE, e.getMessage(), e);
			out.close();
			try {
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			out.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		new Thread(new Runnable() {
			@Override
			public void run() {
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
		}).start();

	}

	@Override
	public void setGameInformation(Map map, Player player) {
		graphics.initialize(map, player);
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

	private static class CommandHandler extends Command {

		public static void handleCommand(SocketInterface si, String line) {
			java.util.Map<String, String> params = translateCommand(line);
			String action = params.get(ACTION);
			switch (action) {
			case MESSAGE:
				Application.println("SERVER) " + params.get(DATA));
				break;
			case PLAYERS:
				Application.println("SERVER) " + params.get(DATA) + " players in this match");
				break;
			case SEND:
				receiveObject(si, params.get(DATA));
				break;
			default:
				break;
			}
		}

		private static void receiveObject(SocketInterface si, String string) {
			switch (string) {
			case GAME_INFORMATION:
				receiveGameInformation(si);
				break;

			default:
				break;
			}

		}

		@SuppressWarnings("unchecked")
		private static void receiveGameInformation(SocketInterface si) {
			try {
				ObjectInputStream ois = new ObjectInputStream(si.getSocket().getInputStream());
				List<Object> info = (List<Object>) ois.readObject();
				ois.close();
				Map map = (Map) info.get(0);
				Player player = (Player) info.get(1);
				si.setGameInformation(map, player);
			} catch (IOException | ClassNotFoundException e) {
				LOG.log(Level.WARNING, e.getMessage(), e);
			}
		}
	}

	@Override
	public void sendToServer(String action, String target) {
		// TODO Auto-generated method stub

	}
}
