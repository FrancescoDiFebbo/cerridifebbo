package it.polimi.ingsw.cerridifebbo.model;

import java.util.Collections;
import java.util.Observable;
import java.util.Scanner;
import java.util.Vector;

public class Deck {
	private final Vector<Card> characterCards;
	private final Vector<Card> sectorCards;
	private final Vector<Card> itemCards;
	private final Vector<Card> escapeHatchCards;

	private int currentCharacter = 0;
	private int currentSector = 0;
	private int currentItem = 0;
	private int currentEscapeHatchCards = 0;

	Deck(Vector<Card> characters, Vector<Card> sectors, Vector<Card> items, Vector<Card> escapeHatches) {
		this.characterCards = characters;
		this.sectorCards = sectors;
		this.itemCards = items;
		this.escapeHatchCards = escapeHatches;

	}
	
	//TODO
	public static void main(String[] args) {
		DeckFactory deckFactory = new ConcreteDeckFactory();
		deckFactory.setPlayers(3);
		Deck deck = deckFactory.createDeck();
		Scanner in = new Scanner(System.in);
		while (!in.next().equals("q")) {
			deck.drawCharacterCard().performAction();
		}
	}

	public Card drawCharacterCard() {
		//TODO
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
