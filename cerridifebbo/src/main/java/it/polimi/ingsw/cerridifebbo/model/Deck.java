package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class Deck {
	private String name;
	private final ArrayList<Card> cards;
	
	public void draw ()
	{
		
	}
	
	public void discardCard ()
	{
		
	}
	
	public Deck(ArrayList<Card> cards){
		this.cards = cards;
	}
	
	public static void main(String[] args) {
		ConcreteDeckFactory deckFactory = new ConcreteDeckFactory(5);
		Deck characterDeck = deckFactory.createDeck(ConcreteDeckFactory.CHARACTER_DECK);
	}

}
