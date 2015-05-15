package it.polimi.ingsw.cerridifebbo.model;

import java.rmi.server.UID;
import java.util.UUID;

public class User {
	private final UUID ID = UUID.randomUUID();
	private Player player;

	public void setPlayer(Player player) {
		this.player = player;
	}
	public Player getPlayer(){
		return player;
	}
}
