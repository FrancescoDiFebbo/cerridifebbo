package it.polimi.ingsw.cerridifebbo.controller.common;

import java.util.HashMap;

public abstract class Command {

	public static final String ACTION = "action";
	public static final String REGISTER = "register";
	public static final String SEND = "send";
	public static final String MESSAGE = "message";
	public static final String WRONG_CALL = "wrong_call";
	public static final String MOVE = "move";
	public static final String SECTOR = "sector";
	public static final String CARD = "card";
	public static final String START_TURN = "start_turn";
	public static final String END_TURN = "end_turn";

	public static final String DATA = "data";
	public static final String GAME_INFORMATION = "game_information";
	public static final String UPDATE = "update";	

	protected static java.util.Map<String, String> translateCommand(String line) {
		
		java.util.Map<String, String> params = new HashMap<String, String>();
		String[] splitted = line.split("&");
		for (int i = 0; i < splitted.length; i++) {
			String first = splitted[i].split("=")[0].replace("_", "\n");
			String second = splitted[i].split("=")[1];
			params.put(first, second == null ? second : second.replace("*", "\n"));
		}
		return params;
	}

	public static String build(String action, String data, String... args) {
		StringBuilder sb = new StringBuilder();
		sb.append(ACTION + "=" + action);
		sb.append("&");
		sb.append(DATA + "=" + data);
		for (int i = 0; i < args.length; i++) {
			sb.append("&");
			sb.append(DATA + i);
			sb.append("=");
			sb.append(args[i]);
		}
		return sb.toString().replace("\n", "*");
	}
}
