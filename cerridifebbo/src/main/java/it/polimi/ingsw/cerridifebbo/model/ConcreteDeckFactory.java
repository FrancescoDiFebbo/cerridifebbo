package it.polimi.ingsw.cerridifebbo.model;

import java.util.Collections;
import java.util.Vector;

public class ConcreteDeckFactory implements DeckFactory {

	public static final String BASIC_GAME_TYPE = "basic_game_type";

	public Deck createDeck(String gameType) {
		return createDeck(gameType, 8);
	}

	public Deck createDeck(String gameType, int numberOfPlayers) {
		// TODO controllare numero giocatori
		switch (gameType) {
		case BASIC_GAME_TYPE:
			return deckCreator(numberOfPlayers, false, false, false);
		default:
			return null;
		}
	}

	private Deck deckCreator(int characters, boolean containsItems, boolean items, boolean escapeHatches) {
		Vector<Card> characterCards = createCharactersDeck(characters);
		Vector<Card> sectorCards = createSectorsDeck(containsItems);
		Vector<Card> itemCards = createItemsDeck(items);
		Vector<Card> escapeHatchCards = createEscapeHatchesDeck(escapeHatches);

		return new Deck(characterCards, sectorCards, itemCards, escapeHatchCards);
	}

	private Vector<Card> createCharactersDeck(int players) {
		Vector<Card> cards = new Vector<Card>();
		for (int i = 0; i < players / 2; i++) {
			cards.add(new CharacterCard(CharacterProperties.HUMAN_RACE, CharacterProperties.HUMAN_NAMES[i]));
		}
		if (players % 2 == 0) {
			for (int i = 0; i < players / 2; i++) {
				cards.add(new CharacterCard(CharacterProperties.ALIEN_RACE, CharacterProperties.ALIEN_NAMES[i]));
			}
		} else {
			for (int i = 0; i < players / 2 + 1; i++) {
				cards.add(new CharacterCard(CharacterProperties.ALIEN_RACE, CharacterProperties.ALIEN_NAMES[i]));
			}
		}
		Collections.shuffle(cards);
		return cards;
	}

	private Vector<Card> createSectorsDeck(boolean containsObject) {
		//TODO carte settore contenenti oggetti
		Vector<Card> cards = new Vector<Card>();
		for (int i = 0; i < SectorProperties.MAX_NOISE_IN_YOUR_SECTOR_CARDS; i++) {
			cards.add(new SectorCard(SectorProperties.NOISE_IN_YOUR_SECTOR_CARD));
		}
		for (int i = 0; i < SectorProperties.MAX_NOISE_IN_ANY_SECTOR_CARDS; i++) {
			cards.add(new SectorCard(SectorProperties.NOISE_IN_ANY_SECTOR_CARD));
		}
		for (int i = 0; i < SectorProperties.MAX_SILENCE_CARDS; i++) {
			cards.add(new SectorCard(SectorProperties.SILENCE_CARD));
		}
		Collections.shuffle(cards);
		return cards;
	}

	private Vector<Card> createItemsDeck(boolean setUp) {
		return null;

	}

	private Vector<Card> createEscapeHatchesDeck(boolean setUp) {
		return null;
	}

	public static class CharacterProperties {
		public static final int MAX_ALIEN_CARDS = 4;
		public static final int MAX_HUMAN_CARDS = 4;

		public static final String ALIEN_RACE = "alien_race";
		public static final String HUMAN_RACE = "human_race";

		public static final String[] HUMAN_NAMES = { "Capitano: Ennio Maria Dominoni", "Pilota: Julia Niguloti a.k.a \"Cabal\"",
				"Psicologo: Silvano Porpora", "Soldato: Tuccio Brandon a.k.a \"Piri\"" };
		public static final String[] ALIEN_NAMES = { "Piero Ceccarella", "Vittorio Martana", "Maria Galbani", "Paolo Landon" };
	}

	public static class SectorProperties {
		public static final int MAX_SECTOR_CARDS = 25;
		public static final int MAX_NOISE_IN_YOUR_SECTOR_CARDS = 10;
		public static final int MAX_NOISE_IN_ANY_SECTOR_CARDS = 10;
		public static final int MAX_SILENCE_CARDS = 5;

		public static final String NOISE_IN_YOUR_SECTOR_CARD = "noise_in_your_sector_card";
		public static final String NOISE_IN_ANY_SECTOR_CARD = "noise_in_any_sector_card";
		public static final String SILENCE_CARD = "silence_card";
	}

	public static class ItemProperties {
		
	}
}
