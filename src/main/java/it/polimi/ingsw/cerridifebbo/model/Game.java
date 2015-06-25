package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.Server;
import it.polimi.ingsw.cerridifebbo.controller.server.User;

import java.util.Calendar;
import java.util.List;

/**
 * The Class Game structured like an FSM, manages the states
 *
 * @author cerridifebbo
 */
public class Game implements Runnable {

	/** The Constant MAX_TURNS. */
	private static final int MAX_TURNS = 39;

	/** The Constant MAX_TIMEOUT. */
	public static final int MAX_TIMEOUT = 60000;

	/** The Constant MAX_PLAYERS. */
	public static final int MAX_PLAYERS = CharacterDeckFactory.MAX_PLAYERS;

	/** The Constant MIN_PLAYERS. */
	public static final int MIN_PLAYERS = CharacterDeckFactory.MIN_PLAYERS;

	/** The users in the game. */
	private final List<User> users;

	/** The current state. */
	private GameState state;

	/** The map in the game. */
	private Map map;

	/** The deck in the game. */
	private Deck deck;

	/** Indicates if the game is ended. */
	private boolean end = false;

	/** The last human in the game. */
	private Player lastHuman;

	/** The thread containing the game. */
	private Thread thread;

	/**
	 * Instantiates a new game.
	 *
	 * @param users
	 *            the users
	 */
	public Game(List<User> users) {
		this.users = users;
	}

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	public Map getMap() {
		return map;
	}

	/**
	 * Sets the map.
	 *
	 * @param map
	 *            the new map
	 */
	public void setMap(Map map) {
		this.map = map;
	}

	/**
	 * Gets the deck.
	 *
	 * @return the deck
	 */
	public Deck getDeck() {
		return deck;
	}

	/**
	 * Sets the deck.
	 *
	 * @param deck
	 *            the new deck
	 */
	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	/**
	 * If the game is ended.
	 *
	 * @return the end
	 */
	public boolean getEnd() {
		return end;
	}

	/**
	 * Sets if the game is ended.
	 *
	 * @param end
	 *            the new end
	 */
	public void setEnd(boolean end) {
		this.end = end;
	}

	/**
	 * Gets the last human in the game.
	 *
	 * @return the last human
	 */
	public Player getLastHuman() {
		return this.lastHuman;
	}

	/**
	 * Sets the last human in the game.
	 *
	 * @param player
	 *            the new last human
	 */
	public void setLastHuman(Player player) {
		this.lastHuman = player;
	}

	/**
	 * Starts the game within a thread.
	 */
	public void start() {
		thread = new Thread(this, "GAME" + Calendar.getInstance().get(Calendar.MILLISECOND));
		thread.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
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

	/**
	 * Manages the player turns and checks the game end.
	 */
	private void turnManage() {
		for (int i = 0; i < MAX_TURNS; i++) {
			for (User user : users) {
				state = new SingleTurn(this, user);
				state.handle();
				state = new CheckGame(this);
				state.handle();
				if (end) {
					break;
				}
			}
			if (end) {
				break;
			}
		}
	}

	/**
	 * Ends the game and asks server to disconnect the users from this game.
	 */
	private void close() {
		Server.getInstance().gameOver(this);
		if (thread != null) {
			thread.interrupt();
		}
	}

	/**
	 * Asks user for sector.
	 *
	 * @param player
	 *            the player
	 * @return the sector
	 */
	public Sector getSector(Player player) {
		User me = findUser(player);
		return me.getSector(map);
	}

	/**
	 * Asks user for card.
	 *
	 * @param player
	 *            the player
	 * @return the card
	 */
	public Move getCard(Player player) {
		User me = findUser(player);
		return me.getCard();
	}

	/**
	 * Sends update to player.
	 *
	 * @param player
	 *            the player
	 * @param card
	 *            the card
	 * @param added
	 *            the added
	 */
	public void updatePlayer(Player player, Card card, boolean added) {
		User me = findUser(player);
		me.updatePlayer(me.getPlayer(), card, added);
	}

	/**
	 * Send hatch update to everybody.
	 *
	 * @param player
	 *            the player
	 */
	public void updateHatch(Player player) {
		for (User user : users) {
			user.updateHatch(player.getPosition());
		}
	}

	/**
	 * Inform players about the state of game.
	 *
	 * @param player
	 *            the player
	 * @param sentence
	 *            the sentence
	 * @param target
	 *            the target
	 */
	public void informPlayers(Player player, Sentence sentence, Object target) {
		User me = findUser(player);
		for (User user : users) {
			String message = null;
			if (user == me && me != null) {
				message = Sentence.toMe(sentence, this, target);
				if (message != null) {
					me.sendMessage(message);
				}
				continue;
			}
			message = Sentence.toOthers(sentence, me, this, target);
			if (message != null) {
				user.sendMessage(message);
			}
		}
	}

	/**
	 * Finds user containing the player passed.
	 *
	 * @param player
	 *            the player
	 * @return the user
	 */
	private User findUser(Player player) {
		if (player == null) {
			return null;
		}
		for (User user : users) {
			if (user.getPlayer() == player) {
				return user;
			}
		}
		return null;
	}
}
