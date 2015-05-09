package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;

public class ConcreteDeckFactory implements DeckFactory {

	public static final String CHARACTER_DECK = "character_deck";
	public static final String ITEM_DECK = "item_deck";
	public static final String SECTOR_DECK = "sector_deck";
	public static final String ESCAPE_HATCH_DECK = "escape_hatch_deck";
	
	private int playersCount;
	
	public ConcreteDeckFactory(int numbersOfPlayers) {
		this.playersCount = numbersOfPlayers;
	}

	public Deck createDeck(String structure) {
		switch (structure) {
		case CHARACTER_DECK:
			return characterDeckCreator();
		default:
			//TODO
			return new Deck(new ArrayList<Card>());
		}
	}

	protected Deck characterDeckCreator() {
		ArrayList<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < playersCount/2; i++) {
			cards.add(new CharacterCard(CharacterAttributes.HUMAN_RACE, CharacterAttributes.humanNames[i]));
		}
		if (playersCount % 2 == 0) {			
			for (int i = 0; i < playersCount / 2; i++) {
				cards.add(new CharacterCard(CharacterAttributes.ALIEN_RACE, CharacterAttributes.alienNames[i]));
			}
		} else {
			for (int i = 0; i < playersCount / 2 + 1; i++) {
				cards.add(new CharacterCard(CharacterAttributes.ALIEN_RACE, CharacterAttributes.alienNames[i]));
			}
		}
		return new Deck(cards);
	}

	private static class CharacterAttributes {
		public static final int MAX_ALIEN_CARDS = 4;
		public static final int MAX_HUMAN_CARDS = 4;

		public static final String ALIEN_RACE = "alien_race";
		public static final String HUMAN_RACE = "human_race";

		public static final String[] humanNames = { "Capitano: Ennio Maria Dominoni", "Pilota: Julia Niguloti a.k.a \"Cabal\"",
				"Psicologo: Silvano Porpora", "Soldato: Tuccio Brandon a.k.a \"Piri\"" };
		public static final String[] alienNames = { "Piero Ceccarella", "Vittorio Martana", "Maria Galbani", "Paolo Landon" };
	}
}
