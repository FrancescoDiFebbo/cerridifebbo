package it.polimi.ingsw.cerridifebbo.controller.common;

public enum ItemCardName {
	ADRENALINE("Adrenaline"), ATTACK("Attack"), DEFENSE("Defense"), SEDATIVES("Sedatives"), SPOTLIGHT("Spotlight"), TELEPORT("Teleport");
	
	private final String name;
	
	ItemCardName(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
