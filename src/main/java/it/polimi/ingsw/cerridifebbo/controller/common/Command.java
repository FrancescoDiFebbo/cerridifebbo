package it.polimi.ingsw.cerridifebbo.controller.common;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class Command provides methods to coding and decoding string commands.
 *
 * @author cerridifebbo
 */
public abstract class Command {

	/** The Constant ACTION. */
	public static final String ACTION = "action";

	/** The Constant DATA. */
	public static final String DATA = "data";

	/** The Constant REGISTER. */
	public static final String REGISTER = "register";

	/** The Constant MOVE. */
	public static final String MOVE = "move";

	/** The Constant MESSAGE. */
	public static final String MESSAGE = "message";

	/** The Constant SEND. */
	public static final String SEND = "send";

	/** The Constant SECTOR. */
	public static final String SECTOR = "sector";

	/** The Constant CARD. */
	public static final String CARD = "card";

	/** The Constant START_TURN. */
	public static final String START_TURN = "start_turn";

	/** The Constant END_TURN. */
	public static final String END_TURN = "end_turn";

	/** The Constant DISCONNECT. */
	public static final String DISCONNECT = "disconnect";

	// target of command "send"
	/** The Constant GAME_INFORMATION. */
	public static final String GAME_INFORMATION = "game_information";

	/** The Constant UPDATE. */
	public static final String UPDATE = "update";

	/** The Constant HATCH. */
	public static final String HATCH = "hatch";

	/**
	 * Instantiates a new command object.
	 */
	protected Command() {

	}

	/**
	 * Translates string command.
	 *
	 * @param line
	 *            the line
	 * @return a map key-value
	 */
	protected static Map<String, String> translateCommand(String line) {
		java.util.Map<String, String> params = new HashMap<String, String>();
		String[] splitted = line.split("&");
		for (int i = 0; i < splitted.length; i++) {
			String first = splitted[i].split("=")[0].replace("*", "\n");
			String second = splitted[i].split("=")[1];
			params.put(first, second == null ? second : second.replace("*", "\n"));
		}
		return params;
	}

	/**
	 * Builds the command from string arguments.
	 *
	 * @param action
	 *            the action
	 * @param data
	 *            the data
	 * @param args
	 *            the args
	 * @return the string
	 */
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
