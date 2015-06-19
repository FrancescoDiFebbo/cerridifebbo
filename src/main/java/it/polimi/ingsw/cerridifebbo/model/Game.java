package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.Server;
import it.polimi.ingsw.cerridifebbo.controller.server.User;

import java.util.Calendar;
import java.util.List;

public class Game implements Runnable {

	private static final int MAX_TURNS = 39;
	public static final int MAX_TIMEOUT = 60000;
	public static final int MAX_PLAYERS = CharacterDeckFactory.MAX_PLAYERS;
	public static final int MIN_PLAYERS = CharacterDeckFactory.MIN_PLAYERS;

	private final List<User> users;
	private GameState state;
	private Map map;
	private Deck deck;
	private boolean end = false;
	private Thread thread;

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

	public boolean getEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public void start() {
		thread = new Thread(this, "GAME" + Calendar.getInstance().get(Calendar.MILLISECOND));
		thread.start();
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
				if (end) {
					break;
				}
			}
			if (end) {
				break;
			}
		}
	}

	private void close() {
		Server.getInstance().gameOver(this);
		if (thread != null) {
			thread.interrupt();
		}
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

	public void informPlayers(Player player, Sentence sentence, Object target) {
		User me = findUser(player);
		for (User user : users) {
			String message = null;
			if (me != null && user == me) {
				message = Sentence.toMe(sentence, this, target);
				if (message != null) {
					me.sendMessage(message);
				}
				continue;
			}
			message = Sentence.toOthers(sentence, me, this, target);
			if (message != null) {
				user.sendMessage("\u2192 " + message);
			}
		}
	}

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
