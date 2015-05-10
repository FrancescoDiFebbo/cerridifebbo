package it.polimi.ingsw.cerridifebbo.model;

public class SectorCard extends Card {
	
	private final String type;
	private final boolean containsItem;
	
	SectorCard(String type) {
		this(type, false);
	}
	
	SectorCard(String type, boolean item) {
		this.type = type;
		this.containsItem = item;
	}
	
	@Override
	public void performAction() {
		// TODO Auto-generated method stub

	}

}
