package it.polimi.ingsw.cerridifebbo.model;

import java.util.UUID;

public class User {
	private final UUID id;
	private Player player;

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
}
