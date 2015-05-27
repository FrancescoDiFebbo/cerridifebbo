package it.polimi.ingsw.cerridifebbo.model;

import java.util.Collections;
import java.util.List;

public class Deck {
	private final List<Card> characterCards;
	private final List<Card> sectorCards;
	private final List<Card> itemCards;
	private final List<Card> escapeHatchCards;

	private int currentCharacter = 0;
	private int currentSector = 0;
	private int currentItem = 0;
	private int currentEscapeHatchCards = 0;

	Deck(List<Card> characters, List<Card> sectors, List<Card> items, List<Card> escapeHatches) {
		this.characterCards = characters;
		this.sectorCards = sectors;
		this.itemCards = items;
		this.escapeHatchCards = escapeHatches;

	}

	public Card drawCharacterCard() {
		return characterCards.get(currentCharacter++);
	}

	public Card drawSectorCard() {
		if (currentSector == sectorCards.size()) {
			Collections.shuffle(sectorCards);
			currentSector = 0;
		}
		return sectorCards.get(currentSector++);
	}

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

	public Card drawEscapeHatchCard() {
		if (currentEscapeHatchCards == escapeHatchCards.size()) {
			Collections.shuffle(escapeHatchCards);
			currentEscapeHatchCards = 0;
		}
		return escapeHatchCards.get(currentEscapeHatchCards++);
	}
}
