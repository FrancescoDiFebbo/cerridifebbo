package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public abstract class Player {

	private CharacterCard playerCard;
	private Card[] ownCards;
	private Sector pos;
	private int maxMovement;
	private boolean alive;

	Player(CharacterCard playerCard, Sector pos, int maxMovement) {
		this.playerCard = playerCard;
		this.pos = pos;
		this.maxMovement = maxMovement;
		this.ownCards = null;
		this.alive= true;

	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void kill(){
		this.alive=false;
	}

	public int getMaxMovement() {
		return maxMovement;
	}

	public Sector getSector() {
		return pos;
	}

	public Sector getPos() {
		return pos;
	}

	public void setPos(Sector pos) {
		this.pos = pos;
	}

	public void setMaxMovement(int maxMovement) {
		this.maxMovement = maxMovement;
	}

	public void attack(ArrayList<User> userList) {
		for (User u : userList) {
			if (u.getPlayer().pos == this.pos) {
				u.getPlayer().kill();
			}
		}
	}

	public boolean movement(Sector destination) {
		if(destination.isReachable(this)){
			this.pos=destination;
			return true;
		}
		return false;
	}
		
		
	
}
