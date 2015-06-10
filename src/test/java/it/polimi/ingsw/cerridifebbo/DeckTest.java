package it.polimi.ingsw.cerridifebbo;

import static org.junit.Assert.*;
import it.polimi.ingsw.cerridifebbo.model.ConcreteDeckFactory;
import it.polimi.ingsw.cerridifebbo.model.Deck;
import it.polimi.ingsw.cerridifebbo.model.DeckFactory;
import it.polimi.ingsw.cerridifebbo.model.HumanCard;

import org.junit.Test;

public class DeckTest {

	@Test
	public void test() {
		DeckFactory deckFactory = new ConcreteDeckFactory();
		int numberOfPlayers = 8;
		Deck deck = deckFactory.createDeck(numberOfPlayers);
		int h = 0;
		int a = 0;
		for (int i = 0; i < numberOfPlayers; i++) {
			if (deck.drawCharacterCard() instanceof HumanCard) {
				h++;
			} else {
				a++;
			}
		}
		assertTrue(h == numberOfPlayers / 2 && (a == numberOfPlayers / 2 || a == (numberOfPlayers / 2) + 1));

	}

}
