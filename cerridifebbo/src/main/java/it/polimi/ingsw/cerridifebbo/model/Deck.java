package it.polimi.ingsw.cerridifebbo.model;

import java.util.Collections;
import java.util.Observable;
import java.util.Scanner;
import java.util.Vector;

public class Deck extends Observable{
	private final Vector<Card> characterCards;
	private int currentCharacter = 0;
	private final Vector<Card> sectorCards;
	private int currentSector = 0;
	private final Vector<Card> itemCards;
	private int currentItem = 0;
	private final Vector<Card> escapeHatchCards;
	private int currentEscapeHatchCards = 0;

	Deck(Vector<Card> characters, Vector<Card> sectors, Vector<Card> items, Vector<Card> escapeHatches) {
		this.characterCards = characters;
		this.sectorCards = sectors;
		this.itemCards = items;
		this.escapeHatchCards = escapeHatches;
		
	}

	public static void main(String[] args) {
		ConcreteDeckFactory deckFactory = new ConcreteDeckFactory();
		Deck deck = deckFactory.createDeck(ConcreteDeckFactory.BASIC_GAME_TYPE);
		Scanner in = new Scanner(System.in);
		while (!in.nextLine().equals("q")) {
			deck.drawCharacterCard().performAction();
		}
	}

	public Card drawCharacterCard() {
		if (currentCharacter == characterCards.size()) {
			Collections.shuffle(characterCards);
			currentCharacter = 0;
		}
		return characterCards.get(currentCharacter++);
	}

	public void drawSectorCard() {

	}

	public void drawItemCard() {

	}

	public void drawEscapeHatchCard() {

	}

	public void discardCard() {

	}
}
