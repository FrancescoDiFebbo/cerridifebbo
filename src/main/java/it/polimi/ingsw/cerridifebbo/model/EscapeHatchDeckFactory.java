package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.Collections;

public class EscapeHatchDeckFactory implements SingleTypeDeckFactory {

	public static final int MAX_GREEN_ESCAPE_HATCH_CARDS = 3;
	public static final int MAX_RED_ESCAPE_HATCH_CARDS = 3;
	public static final int MAX_ESCAPE_HATCH_CARDS = MAX_GREEN_ESCAPE_HATCH_CARDS + MAX_RED_ESCAPE_HATCH_CARDS;

	@Override
	public ArrayList<Card> createDeck() {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < MAX_GREEN_ESCAPE_HATCH_CARDS; i++) {
			cards.add(new GreenEscapeHatchCard());
		}
		for (int i = 0; i < MAX_RED_ESCAPE_HATCH_CARDS; i++) {
			cards.add(new RedEscapeHatchCard());
		}
		Collections.shuffle(cards);
		return cards;
	}

}
