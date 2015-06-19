package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Card;

import java.io.Serializable;

public class ItemCardRemote implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private final String name;

	public ItemCardRemote(Card itemCard) {
		this.name = itemCard.toString();
	}

	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
