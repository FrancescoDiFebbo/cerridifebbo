package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.Server;
import it.polimi.ingsw.cerridifebbo.controller.server.User;

import java.util.List;

public class Game implements Runnable {

	private static final int MAX_TURNS = 39;
	public static final int MAX_TIMEOUT = 30000;
	public static final int MAX_PLAYERS = CharacterDeckFactory.MAX_PLAYERS;
	public static final int MIN_PLAYERS = CharacterDeckFactory.MIN_PLAYERS;

	private final List<User> users;

	private GameState state;
	private Map map;
	private Deck deck;
	private Boolean end = false;

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}

	public Game(List<User> users) {
		this.users = users;
	}

	public List<User> getUsers() {
		return users;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	@Override
	public void run() {
		state = new StartGame(this);
		state.handle();
		turnManage();
		end = true;
		state = new EndGame(this);
		state.handle();	
		close();
	}

	private void turnManage() {
		for (int i = 0; i < MAX_TURNS; i++) {
			for (User user : users) {
				state = new SingleTurn(this, user);
				state.handle();
				state = new CheckGame(this);
				state.handle();
				if (end == true) {
					break;
				}
			}
			if (end == true) {
				break;
			}
		}
	}
	
	private void close() {
		Server.getInstance().gameOver(this);
	}

	public Sector getSector(Player player) {
		User me = findUser(player);
		return me.getSector(map);
	}

	public Move getCard(Player player) {
		User me = findUser(player);
		return me.getCard();
	}

	public void updatePlayer(Player player, Card card, boolean added) {
		User me = findUser(player);
		me.updatePlayer(me.getPlayer(), card, added);
	}

	public void sendMessage(Player player, String message) {
		if (player == null) {
			for (User user : users) {
				user.sendMessage(message);
			}
		} else {
			User user = findUser(player);
			user.sendMessage(message);
		}
	}

	public void informPlayers(Player player, String sentence, Sector sector) {
		User me = findUser(player);
		for (User user : users) {
			if (user == me) {
				me.sendMessage(Sentence.toMe(sentence, this, sector));

				continue;
			}
			user.sendMessage("GAME) "
					+ Sentence.toOthers(sentence, me, this, sector));

		}
	}

	private User findUser(Player player) {
		for (User user : users) {
			if (user.getPlayer() == player) {
				return user;
			}
		}
		return null;
	}

	public static class Sentence {
		public static final String KILLED = "killed";
		public static final String ADRENALINE = "adrenaline";
		public static final String ATTACK_CARD = "attack_card";
		public static final String DEFENSE_CARD = "defense_card";
		public static final String ESCAPED = "escaped";
		public static final String TELEPORT_CARD = "teleport_card";
		public static final String SPOTLIGHT_CARD = "spotlight_card";
		public static final String SEDATIVES_CARD = "sedatives_card";
		public static final String NOT_ESCAPED = "not_escaped";
		public static final String NOISE_IN = "noise_in";
		public static final String NOISE_ANY = "noise_any";
		public static final String ATTACK = "attack";
		public static final String TIMEFINISHED = "time_finished";
		public static final String DISCARD_CARD = "discard_card";

		private Sentence() {

		}

		public static String toMe(String sentence, Game game, Sector sector) {
			switch (sentence) {
			case NOISE_IN:
				return "You made noise in your sector";
			case NOISE_ANY:
				return "You made noise in " + sector;
			case KILLED:
				return "You are dead";
			case ADRENALINE:
				return "You have used adrenaline";
			case ATTACK_CARD:
				return "You have used attack card";
			case DEFENSE_CARD:
				return "You have used defense card";
			case ESCAPED:
				return "Escape hatch is open. You have escaped";
			case TELEPORT_CARD:
				return "You have used teleport card";
			case SPOTLIGHT_CARD:
				return "You have used spotlight card" + spotlight(game, sector);
			case SEDATIVES_CARD:
				return "You have used sedatives card";
			case NOT_ESCAPED:
				return "Escape hatch is closed. You can't escape.";
			case ATTACK:
				return "You are attacking";
			case TIMEFINISHED:
				return "Time is over.";
			case DISCARD_CARD:
				return "You have discarded a card";
			default:
				return null;
			}
		}

		public static String toOthers(String sentence, User user, Game game,
				Sector sector) {
			String name = null;
			if (user.getPlayer().isRevealed()) {
				name = user.getName() + " ("
						+ user.getPlayer().getPlayerCard().getCharacterName()
						+ ")";
			} else {
				name = user.getName();
			}
			switch (sentence) {
			case NOISE_IN:
				return name + " made noise in " + sector;
			case NOISE_ANY:
				return name + " made noise in " + sector;
			case KILLED:
				return name + " is dead";
			case ADRENALINE:
				return name + " has used adrenaline";
			case ATTACK_CARD:
				return name + " has used attack card";
			case DEFENSE_CARD:
				return name + " has used defense card";
			case ESCAPED:
				return name + " reached " + sector + ". It is open. " + name
						+ " has escaped";
			case TELEPORT_CARD:
				return name + " has used teleport card";
			case SPOTLIGHT_CARD:
				return name + " has used spotlight card"
						+ spotlight(game, sector);
			case SEDATIVES_CARD:
				return name + " has used sedatives card";
			case NOT_ESCAPED:
				return name + " reached " + sector + ". It is closed. " + name
						+ " can't escape.";
			case ATTACK:
				return name + " is attacking " + sector;
			case TIMEFINISHED:
				return name + " has finished his turn time";
			case DISCARD_CARD:
				return name + " has discarded a card";
			default:
				return null;
			}
		}

		private static String spotlight(Game game, Sector sector) {
			StringBuilder build = new StringBuilder();
			List<Sector> sectors = sector.getAdjacentSectors();
			sectors.add(sector);
			for (User user : game.getUsers()) {
				Player p = user.getPlayer();
				if (sectors.contains(p.getPosition())) {
					String name = null;
					if (user.getPlayer().isRevealed()) {
						name = user.getPlayer().getPlayerCard()
								.getCharacterName();
					} else {
						name = user.getName().toString().split("-")[0];
					}
					build.append("\n" + name + " is in " + p.getPosition()
							+ "\n");
				}
			}
			return build.toString();
		}
	}
}
