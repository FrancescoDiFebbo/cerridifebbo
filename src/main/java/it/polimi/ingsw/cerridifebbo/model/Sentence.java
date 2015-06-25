package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.User;

import java.util.List;

/**
 * The Enum Sentence contains the sentences to be sent to clients during the
 * game.
 *
 * @author cerridifebbo
 */
public enum Sentence {

	NOISE_IN, NOISE_ANY, ADRENALINE, ATTACK_CARD, DEFENSE_CARD, SEDATIVES_CARD, SPOTLIGHT_CARD, TELEPORT_CARD, DISCARD_CARD, ATTACK, KILLED, ESCAPED, NOT_ESCAPED, TIMEFINISHED, DISCONNECTED, STARTING_GAME, MOVEMENT, RECEIVED_CARD, CONNECTED;

	/**
	 * Returns a String directed to the current playing client.
	 *
	 * @param sentence
	 *            the sentence
	 * @param game
	 *            the game
	 * @param target
	 *            the target
	 * @return the message
	 */
	public static String toMe(Sentence sentence, Game game, Object target) {
		switch (sentence) {
		case STARTING_GAME:
			return "Game is starting";
		case MOVEMENT:
			return "You moved to " + target;
		case RECEIVED_CARD:
			return "You received " + target + " card";
		case NOISE_IN:
			return "You made noise in your sector";
		case NOISE_ANY:
			return "You made noise in " + target;
		case ADRENALINE:
			return "You have used adrenaline";
		case ATTACK_CARD:
			return "You have used attack card";
		case DEFENSE_CARD:
			return "You have used defense card";
		case SEDATIVES_CARD:
			return "You have used sedatives card";
		case SPOTLIGHT_CARD:
			return "You have used spotlight card" + spotlight(game, (Sector) target);
		case TELEPORT_CARD:
			return "You have used teleport card";
		case DISCARD_CARD:
			return "You have discarded a card";
		case ATTACK:
			return "You are attacking";
		case KILLED:
			return "You are dead";
		case ESCAPED:
			return "Escape hatch is open. You have escaped";
		case NOT_ESCAPED:
			return "Escape hatch is closed. You can't escape.";
		case TIMEFINISHED:
			return "Time is over.";
		case CONNECTED:
			return "You rejoined the match";
		case DISCONNECTED:
			return "You are disconnected";
		default:
			return null;
		}
	}

	/**
	 * Returns a String directed to other players during a player turn.
	 *
	 * @param sentence
	 *            the sentence
	 * @param user
	 *            the user
	 * @param game
	 *            the game
	 * @param target
	 *            the target
	 * @return the message
	 */
	public static String toOthers(Sentence sentence, User user, Game game, Object target) {
		String name = null;
		if (user != null) {
			name = nameRevealed(user);
		}
		switch (sentence) {
		case STARTING_GAME:
			return "Game is starting...";
		case MOVEMENT:
			return null;
		case RECEIVED_CARD:
			return name + ": has received a card";
		case NOISE_IN:
			return name + ": made noise in " + target;
		case NOISE_ANY:
			return name + ": made noise in " + target;
		case ADRENALINE:
			return name + ": has used adrenaline";
		case ATTACK_CARD:
			return name + ": has used attack card";
		case DEFENSE_CARD:
			return name + ": has used defense card";
		case SEDATIVES_CARD:
			return name + ": has used sedatives card";
		case SPOTLIGHT_CARD:
			return name + ": has used spotlight card" + spotlight(game, (Sector) target);
		case TELEPORT_CARD:
			return name + ": has used teleport card";
		case DISCARD_CARD:
			return name + ": has discarded a card";
		case ATTACK:
			return name + ": is attacking " + target;
		case KILLED:
			return name + ": is dead";
		case ESCAPED:
			return name + ": reached " + target + ". It is open. " + name + " has escaped";
		case NOT_ESCAPED:
			return name + ": reached " + target + ". It is closed. " + name + " can't escape.";
		case TIMEFINISHED:
			return name + ": has finished his turn time";
		case CONNECTED:
			return name + ": rejoined the match";
		case DISCONNECTED:
			return name + ": disconnected from match";
		default:
			return null;
		}
	}

	/**
	 * If a spotlight item has been used the method sends messages to all
	 * players about the effect of the spotlight.
	 *
	 * @param game
	 *            the game
	 * @param sector
	 *            the sector
	 * @return the message
	 */
	private static String spotlight(Game game, Sector sector) {
		StringBuilder build = new StringBuilder();
		List<Sector> sectors = sector.getAdjacentSectors();
		sectors.add(sector);
		for (User user : game.getUsers()) {
			Player p = user.getPlayer();
			if (sectors.contains(p.getPosition())) {
				String name = nameRevealed(user);
				build.append("\n    " + name + " is in " + p.getPosition());
			}
		}
		return build.toString();
	}

	/**
	 * Checks if a players is revealed.
	 *
	 * @param user
	 *            the user
	 * @return the string
	 */
	private static String nameRevealed(User user) {
		Player player = user.getPlayer();
		if (player.isRevealed()) {
			return user.getName() + " (" + (player instanceof HumanPlayer ? "human" : "alien") + ")";
		} else {
			return user.getName();
		}
	}
}
