package it.polimi.ingsw.cerridifebbo.controller.server;

import java.util.Map;
import java.util.UUID;

public class CommandHandler {
	private static final String ACTION = "action";
	private static final String REGISTER = "register";
	private static final String WRONG_CALL = "wrong_call";

	public static String handleCommand(SocketHandler handler, Map<String, String> params) {
		String action = params.get(ACTION);
		switch (action) {
		case REGISTER:
			UUID id = UUID.fromString(params.get("id"));
			boolean registered = handler.getServer().registerClientOnServer(id, handler);
			return registered? "Client registered" : "Client not registered";
		default:
			return WRONG_CALL;
		}
	}

}
