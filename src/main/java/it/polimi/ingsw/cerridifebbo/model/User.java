package it.polimi.ingsw.cerridifebbo.model;

import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

public class User {
	private final UUID id;
	private Player player;
	private Queue<Move> queue;

	public User(UUID id) {
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
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
