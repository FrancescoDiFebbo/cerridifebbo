package it.polimi.ingsw.cerridifebbo.model;

import java.util.Collections;
import java.util.List;

/**
 * This class describes a deck. The deck is composed of character cards, sector
 * cards, item cards, and escape hatch cards.
 * 
 * @author cerridifebbo
 *
 */
public class Deck {
	private final List<Card> characterCards;
	private final List<Card> sectorCards;
	private final List<Card> itemCards;
	private final List<Card> escapeHatchCards;

	private int currentCharacter = 0;
	private int currentSector = 0;
	private int currentItem = 0;
	private int currentEscapeHatch = 0;

	/**
	 * This constructor set the cards of the deck.
	 * 
	 * @author cerridifebbo
	 *
	 * @param characters
	 *            a list of characters cards
	 * @param sectors
	 *            a list of sectors cards
	 * @param items
	 *            a list of items cards
	 * @param escapeHatches
	 *            a list of escape hatch cards
	 */
	Deck(List<Card> characters, List<Card> sectors, List<Card> items,
			List<Card> escapeHatches) {
		this.characterCards = characters;
		this.sectorCards = sectors;
		this.itemCards = items;
		this.escapeHatchCards = escapeHatches;

	}

	/**
	 * This method draws a character card.
	 * 
	 * @author cerridifebbo
	 *
	 * @return the card
	 */
	public Card drawCharacterCard() {
		return characterCards.get(currentCharacter++);
	}

	/**
	 * This method draws a sector card.
	 * 
	 * @author cerridifebbo
	 *
	 * @return the card
	 */
	public Card drawSectorCard() {
		if (currentSector == sectorCards.size()) {
			Collections.shuffle(sectorCards);
			currentSector = 0;
		}
		return sectorCards.get(currentSector++);
	}

	/**
	 * This method draws a item card.
	 * 
	 * @author cerridifebbo
	 *
	 * @return the card
	 */
	public Card drawItemCard() {
		int maxItemCards = itemCards.size();
		for (int i = 0; i < maxItemCards; i++) {
			if (currentItem == itemCards.size()) {
				Collections.shuffle(itemCards);
				currentItem = 0;
			}
			ItemCard card = (ItemCard) itemCards.get(currentItem++);
			if (!card.isTaken()) {
				return card;
			}
		}
		return null;
	}

	/**
	 * This method draws a escape hatch card.
	 * 
	 * @author cerridifebbo
	 *
	 * @return the card
	 */
	public Card drawEscapeHatchCard() {
		if (currentEscapeHatch == escapeHatchCards.size()) {
			Collections.shuffle(escapeHatchCards);
			currentEscapeHatch = 0;
		}
		return escapeHatchCards.get(currentEscapeHatch++);
	}

	/**
	 * This method resets the deck.
	 * 
	 * @author cerridifebbo
	 *
	 */
	public void reset() {
		currentCharacter = 0;
		currentEscapeHatch = 0;
		currentItem = 0;
		currentSector = 0;
		Collections.shuffle(characterCards);
		Collections.shuffle(escapeHatchCards);
		Collections.shuffle(itemCards);
		Collections.shuffle(sectorCards);
		for (Card card : itemCards) {
			((ItemCard) card).setTaken(false);
		}

	}
}
