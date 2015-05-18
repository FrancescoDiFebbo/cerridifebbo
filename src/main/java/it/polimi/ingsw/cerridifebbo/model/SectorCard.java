package it.polimi.ingsw.cerridifebbo.model;

public abstract class SectorCard implements Card {

	private final boolean containsItem;

	SectorCard(boolean item) {
		this.containsItem = item;
	}

	public boolean containsItem() {
		return containsItem;
	}

	public boolean isContainsItem() {
		return containsItem;
	}

}
