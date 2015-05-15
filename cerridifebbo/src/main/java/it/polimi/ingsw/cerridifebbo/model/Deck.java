package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Scanner;
import java.util.Vector;

public class Deck {
	private final ArrayList<Card> characterCards;
	private final ArrayList<Card> sectorCards;
	private final ArrayList<Card> itemCards;
	private final ArrayList<Card> escapeHatchCards;

	private int currentCharacter = 0;
	private int currentSector = 0;
	private int currentItem = 0;
	private int currentEscapeHatchCards = 0;

	Deck(ArrayList<Card> characters, ArrayList<Card> sectors, ArrayList<Card> items, ArrayList<Card> escapeHatches) {
		this.characterCards = characters;
		this.sectorCards = sectors;
		this.itemCards = items;
		this.escapeHatchCards = escapeHatches;

	}

	public Card drawCharacterCard() {
		// TODO
		if (currentCharacter == characterCards.size()) {
			Collections.shuffle(characterCards);
			currentCharacter = 0;
		}
		return characterCards.get(currentCharacter++);
	}

	public Card drawSectorCard() {
		return null;
	}

	public Card drawItemCard() {
		return null;
	}

	public Card drawEscapeHatchCard() {
		return null;
	}

	public void discardCard() {

	}
}
