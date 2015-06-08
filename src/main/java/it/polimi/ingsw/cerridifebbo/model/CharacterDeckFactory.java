package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharacterDeckFactory implements SingleTypeDeckFactory {

	private static final int MAX_HUMAN_CARDS = 4;
	private static final int MAX_ALIEN_CARDS = 4;
	public static final int MAX_PLAYERS = MAX_HUMAN_CARDS + MAX_ALIEN_CARDS;
	public static final int MIN_PLAYERS = 2;

	private static final String[] HUMAN_NAMES = { "Capitano Ennio Maria Dominoni", "Pilota Julia Niguloti a.k.a \"Cabal\"",
			"Psicologo Silvano Porpora", "Soldato Tuccio Brandon a.k.a \"Piri\"" };
	private static final String[] ALIEN_NAMES = { "Piero Ceccarella", "Vittorio Martana", "Maria Galbani", "Paolo Landon" };

	@Override
	public List<Card> createDeck(Integer numberOfPlayers) {
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < numberOfPlayers / 2; i++) {
			cards.add(new HumanCard(HUMAN_NAMES[i]));
		}
		if (numberOfPlayers % 2 == 0) {
			for (int i = 0; i < numberOfPlayers / 2; i++) {
				cards.add(new AlienCard(ALIEN_NAMES[i]));
			}
		} else {
			for (int i = 0; i < numberOfPlayers / 2 + 1; i++) {
				cards.add(new AlienCard(ALIEN_NAMES[i]));
			}
		}
		Collections.shuffle(cards);
		return cards;
	}
}
