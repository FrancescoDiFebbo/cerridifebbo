package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.common.CharacterName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class describes a character deck factory. It implements
 * SingleTypeDeckFactory.
 * 
 * @see SingleTypeDeckFactory
 * 
 * @author cerridifebbo
 *
 */
public class CharacterDeckFactory implements SingleTypeDeckFactory {

	private static final int MAX_HUMAN_CARDS = 4;
	private static final int MAX_ALIEN_CARDS = 4;
	public static final int MAX_PLAYERS = MAX_HUMAN_CARDS + MAX_ALIEN_CARDS;
	public static final int MIN_PLAYERS = 2;

	/**
	 * This method creates a list of character cards.
	 * 
	 * @author cerridifebbo
	 * @param numberOfPlayers
	 *            the number of players
	 * @return the list of character cards
	 */
	@Override
	public List<Card> createDeck(Integer numberOfPlayers) {
		List<Card> cards = new ArrayList<Card>();

		for (int i = 0; i < numberOfPlayers / 2; i++) {
			cards.add(new HumanCard(CharacterName.getHumanNames().get(i)));
		}
		if (numberOfPlayers % 2 == 0) {
			for (int i = 0; i < numberOfPlayers / 2; i++) {
				cards.add(new AlienCard(CharacterName.getAlienNames().get(i)));
			}
		} else {
			for (int i = 0; i < numberOfPlayers / 2 + 1; i++) {
				cards.add(new AlienCard(CharacterName.getAlienNames().get(i)));
			}
		}
		Collections.shuffle(cards);
		return cards;
	}
}
