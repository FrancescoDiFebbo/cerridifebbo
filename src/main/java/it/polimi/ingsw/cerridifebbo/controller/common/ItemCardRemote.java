package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.ItemCard;

import java.io.Serializable;

/**
 * The Class ItemCardRemote is a lightweight class for sharing from server to
 * client.
 *
 * @author cerridifebbo
 * @see ItemCard
 */
public class ItemCardRemote implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The name of the card. */
	private final String name;

	/**
	 * Instantiates a new item card remote.
	 *
	 * @param itemCard
	 *            the item card
	 */
	public ItemCardRemote(Card itemCard) {
		this.name = itemCard.toString();
	}

	/**
	 * Gets the name of the card.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
