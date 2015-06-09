package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.server.ServerConnection;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class User {
	private final UUID id;
	private final ServerConnection connection;
	private Player player;
	private Queue<Move> queue = new LinkedList<Move>();
	private boolean online = true;
	private boolean timeFinished = false;

	public User(UUID id, ServerConnection connection) {
		this.id = id;
		this.connection = connection;
	}

	public UUID getId() {
		return id;
	}
	
	public ServerConnection getConnection() {
		return connection;
	}

	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}	

	public synchronized Move getMove() {
		return queue.poll();
	}

	public synchronized void putMove(Move move) {
		queue.offer(move);
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public synchronized void clear(){
		queue.clear();
		timeFinished = false;
		if (player instanceof HumanPlayer) {
			((HumanPlayer) player).clear();
		}
	}

	public boolean isTimeFinished() {
		return timeFinished;
	}

	public void setTimeFinished(boolean timeFinished) {
		this.timeFinished = timeFinished;
	}
}
