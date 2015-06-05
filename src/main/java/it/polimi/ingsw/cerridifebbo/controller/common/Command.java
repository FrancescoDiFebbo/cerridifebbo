package it.polimi.ingsw.cerridifebbo.controller.common;

import java.util.HashMap;

public abstract class Command {
	
	public static final String ACTION = "action";
	public static final String PLAYERS = "players";
	public static final String REGISTER = "register";
	public static final String SEND = "send";
	public static final String MESSAGE = "message";
	public static final String WRONG_CALL = "wrong_call";

	public static final String DATA = "data";
	public static final String GAME_INFORMATION = "game_information";	

	protected static java.util.Map<String, String> translateCommand(String line) {
		java.util.Map<String, String> params = new HashMap<String, String>();
		String[] splitted = line.split("&");
		for (int i = 0; i < splitted.length; i++) {
			params.put(splitted[i].split("=")[0], splitted[i].split("=")[1]);
		}
		return params;
	}

	public static String build(String action, String data, String... args) {
		StringBuilder sb = new StringBuilder();
		sb.append(ACTION + "=" + action);
		sb.append("&");
		sb.append(DATA + "=" + data);
		for (int i = 0; i < args.length - 1; i += 2) {
			sb.append("&");
			sb.append(args[i]);
			sb.append("=");
			sb.append(args[i + 1]);
		}
		return sb.toString();
	}
}
