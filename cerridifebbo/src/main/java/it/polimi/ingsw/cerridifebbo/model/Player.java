package it.polimi.ingsw.cerridifebbo.model;

public class Player {
	private String name;
	private CharacterCard playerCard;
	private Card[] ownCard;
	private Sector pos;
	private String race;
	private int maxMovement;

	public int getMaxMovement() {
		return maxMovement;
	}
	
	public Sector getSector (){
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

	public void move() {

	}

	private boolean attack() {
		return false;
	}

	private void movement() {

	}
}
