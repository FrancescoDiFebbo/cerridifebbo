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

	public Move getMove() {
		if (queue == null) {
			return null;
		}
		return queue.poll();
	}

	public void putMove(Move move) {
		if (queue == null) {
			queue = new LinkedList<Move>();
		}
		queue.offer(move);
	}
}
