package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.Server;

import java.util.List;
import java.util.Random;

public class Game implements Runnable {

	private static final int MAX_TURNS = 39;
	public static final int MAX_TIMEOUT = 20000;

	private final Server server;
	private final List<User> users;

	private GameState state;
	private Map map;
	private Deck deck;
	private int turn = 0;

	public Game(Server server, List<User> users) {
		this.server = server;
		this.state = new StartGame(this);
		this.users = users;
	}

	public boolean serverIsOn() {
		return server != null;
	}

	public List<User> getUsers() {
		return users;
	}

	public GameState getState() {
		return state;
	}

	public void nextTurn() {
		if (turn++ == MAX_TURNS) {
			state = new EndGame(this);
		} else {
			state = new Turn(this);
		}
		state.handle();
	}

	public void checkGame() {
		state = new CheckGame(this);
		state.handle();
	}

	public void endGame() {
		state = new EndGame(this);
		state.handle();
		if (serverIsOn() ) {
			server.gameOver(this);
		}		
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
		state.handle();
	}

	public Sector retrieveSector(Player player) {
		User user = findUser(player);
		Sector sector = null;
		do {
			askForSector(user);
			sector = getSector(user);
		} while (sector==null);		
		return sector;
	}

	private void askForSector(User user) {	
		if (!user.isTimeFinished() && serverIsOn()) {
			user.getConnection().askForSector(user);
		} else {
			user.putMove(new Move(Move.SECTOR, randomSector().toString()));
		}
	}
	
	private Sector getSector(User user) {
		Move move = null;
		do {
			move = user.getMove();
		} while (move == null);
		if (Move.SECTOR.equals(move.getAction())) {
			return map.getCell(move.getTarget());
		}
		return null;

	}
	
	private Sector randomSector() {
		Random random = new Random();
		Sector sector = null;
		do {
			sector = getMap().getCell(random.nextInt(Map.ROWMAP), random.nextInt(Map.COLUMNMAP));
		} while (sector == null);
		return sector;
	}
	
	public void sendGameInformation(int size, Map map, User user) {
		if (serverIsOn()) {
			user.getConnection().sendGameInformation(user, size, map);
		}		
	}
	
	public void updatePlayer(Player player, Card card, boolean added) {
		if (serverIsOn()) {
			User me = findUser(player);
			me.getConnection().updatePlayer(me, card, added);
		}
	}
	
	public void sendMessage(Player player, String message) {
		if (serverIsOn()) {
			if (player == null) {
				for (User user : users) {
					user.getConnection().sendMessage(user, message);
				}
			} else {
				User user = findUser(player);
				user.getConnection().sendMessage(user, message);
			}			
		}
	}

	public void informPlayers(Player player, String sentence, Sector sector) {
		if (serverIsOn()) {
			User me = findUser(player);
			for (User user : users) {
				if (user == me) {
					me.getConnection().sendMessage(me, Sentence.toMe(sentence, this, sector));
					continue;
				}
				user.getConnection().sendMessage(user, "GAME) " + Sentence.toOthers(sentence, me, this, sector));
			}
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
			default:
				return null;
			}
		}

		public static String toOthers(String sentence, User user, Game game, Sector sector) {
			String name = null;
			if (user.getPlayer().isRevealed()) {
				name = user.getPlayer().getPlayerCard().getCharacterName();
			} else {
				name = user.getId().toString().split("-")[0];
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
				return name + " reached " + sector + ". It is open. " + name + " has escaped";
			case TELEPORT_CARD:
				return name + " has used teleport card";
			case SPOTLIGHT_CARD:
				return name + " has used spotlight card" + spotlight(game, sector);
			case SEDATIVES_CARD:
				return name + " has used sedatives card";
			case NOT_ESCAPED:
				return name + " reached " + sector + ". It is closed. " + name + " can't escape.";
			case ATTACK:
				return name + " is attacking " + sector;
			case TIMEFINISHED:
				return name + " has finished his turn time";
			default:
				return null;
			}
		}

		private static String spotlight(Game game, Sector sector) {
			StringBuilder build = new StringBuilder();
			List<Sector> sectors = sector.getAdjacentSectors();
			for (User user : game.getUsers()) {
				Player p = user.getPlayer();
				if (sectors.contains(p.getPosition())) {
					String name = null;
					if (user.getPlayer().isRevealed()) {
						name = user.getPlayer().getPlayerCard().getCharacterName();
					} else {
						name = user.getId().toString().split("-")[0];
					}
					build.append("\n" + name + " is in " + p.getPosition() + "\n");
				}
			}
			return build.toString();
		}
	}
}
