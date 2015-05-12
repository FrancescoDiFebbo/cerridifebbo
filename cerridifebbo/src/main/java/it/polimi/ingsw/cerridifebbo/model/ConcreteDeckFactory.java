package it.polimi.ingsw.cerridifebbo.model;

import java.util.Collections;
import java.util.Vector;

public class ConcreteDeckFactory implements DeckFactory {

	public static final String BASIC_GAME_TYPE = "basic_game_type";
	public static final String ADVANCED_GAME_TYPE = "advanced_game_type";
	public static final int MAX_PLAYERS = 8;
	public static final int MIN_PLAYERS = 2;

	public Deck createDeck(String gameType) {
		return createDeck(gameType, MAX_PLAYERS);
	}

	public Deck createDeck(String gameType, int numberOfPlayers) {
		if (numberOfPlayers < MIN_PLAYERS || numberOfPlayers > MAX_PLAYERS) {
			throw new IllegalArgumentException("Number of players between " + MIN_PLAYERS + " and " + MAX_PLAYERS);
		}
		switch (gameType) {
		case BASIC_GAME_TYPE:
			return getDeck(numberOfPlayers, false, false, false);
		case ADVANCED_GAME_TYPE:
			return getDeck(numberOfPlayers, true, true, true);
		default:
			return null;
		}
	}

	private Deck getDeck(int players, boolean containsItems, boolean items, boolean escapeHatches) {
		Vector<Card> characterCards = createCharactersDeck(players);
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

	private Vector<Card> createSectorsDeck(boolean containsItems) {
		// TODO migliorare il codice
		Vector<Card> cards = new Vector<Card>();
		for (int i = 0; i < SectorProperties.MAX_SILENCE_CARDS; i++) {
			cards.add(new SectorCard(SectorProperties.SILENCE_CARD));
		}

		for (int i = 0; i < SectorProperties.MAX_NOISE_IN_YOUR_SECTOR_CARDS; i++) {
			if (i < SectorProperties.MAX_SECTOR_ITEM_CARDS) {
				cards.add(new SectorCard(SectorProperties.NOISE_IN_YOUR_SECTOR_CARD, containsItems));
			} else {
				cards.add(new SectorCard(SectorProperties.NOISE_IN_YOUR_SECTOR_CARD));
			}
		}

		for (int i = 0; i < SectorProperties.MAX_NOISE_IN_ANY_SECTOR_CARDS; i++) {
			if (i < SectorProperties.MAX_SECTOR_ITEM_CARDS) {
				cards.add(new SectorCard(SectorProperties.NOISE_IN_ANY_SECTOR_CARD, containsItems));
			} else {
				cards.add(new SectorCard(SectorProperties.NOISE_IN_ANY_SECTOR_CARD));
			}
		}

		Collections.shuffle(cards);
		return cards;
	}

	private Vector<Card> createItemsDeck(boolean setUp) {
		if (setUp) {
			// TODO eliminare codice ripetuto, utilizzare una combinazione
			// Key,Value
			Vector<Card> cards = new Vector<Card>();
			for (int i = 0; i < ItemProperties.MAX_ATTACK_ITEM_CARDS; i++) {
				cards.add(new ItemCard(ItemProperties.ATTACK_ITEM_CARD));
			}
			for (int i = 0; i < ItemProperties.MAX_TELEPORT_ITEM_CARDS; i++) {
				cards.add(new ItemCard(ItemProperties.TELEPORT_ITEM_CARD));
			}
			for (int i = 0; i < ItemProperties.MAX_SEDATIVES_ITEM_CARDS; i++) {
				cards.add(new ItemCard(ItemProperties.SEDATIVES_ITEM_CARD));
			}
			for (int i = 0; i < ItemProperties.MAX_SPOTLIGHT_ITEM_CARDS; i++) {
				cards.add(new ItemCard(ItemProperties.SPOTLIGHT_ITEM_CARD));
			}
			for (int i = 0; i < ItemProperties.MAX_DEFENSE_ITEM_CARDS; i++) {
				cards.add(new ItemCard(ItemProperties.DEFENSE_ITEM_CARD));
			}
			for (int i = 0; i < ItemProperties.MAX_ADRENALINE_ITEM_CARDS; i++) {
				cards.add(new ItemCard(ItemProperties.ADRENALINE_ITEM_CARD));
			}
			Collections.shuffle(cards);
			return cards;
		}
		return null;
	}

	private Vector<Card> createEscapeHatchesDeck(boolean setUp) {
		if (setUp) {
			Vector<Card> cards = new Vector<Card>();
			for (int i = 0; i < EscapeHatchProperties.MAX_GREEN_ESCAPE_HATCH_CARDS; i++) {
				cards.add(new EscapeHatchCard(EscapeHatchProperties.GREEN_ESCAPE_HATCH_CARD));
			}
			for (int i = 0; i < EscapeHatchProperties.MAX_RED_ESCAPE_HATCH_CARDS; i++) {
				cards.add(new EscapeHatchCard(EscapeHatchProperties.RED_ESCAPE_HATCH_CARD));
			}
			Collections.shuffle(cards);
			return cards;
		}
		return null;
	}

	public static class CharacterProperties {
		public static final int MAX_HUMAN_CARDS = 4;
		public static final int MAX_ALIEN_CARDS = 4;

		public static final String HUMAN_RACE = "human_race";
		public static final String ALIEN_RACE = "alien_race";

		public static final String[] HUMAN_NAMES = { "Capitano: Ennio Maria Dominoni", "Pilota: Julia Niguloti a.k.a \"Cabal\"",
				"Psicologo: Silvano Porpora", "Soldato: Tuccio Brandon a.k.a \"Piri\"" };
		public static final String[] ALIEN_NAMES = { "Piero Ceccarella", "Vittorio Martana", "Maria Galbani", "Paolo Landon" };
	}

	public static class SectorProperties {
		public static final int MAX_SECTOR_CARDS = 25;
		public static final int MAX_NOISE_IN_YOUR_SECTOR_CARDS = 10;
		public static final int MAX_NOISE_IN_ANY_SECTOR_CARDS = 10;
		public static final int MAX_SILENCE_CARDS = 5;
		public static final int MAX_SECTOR_ITEM_CARDS = 4;

		public static final String NOISE_IN_YOUR_SECTOR_CARD = "noise_in_your_sector_card";
		public static final String NOISE_IN_ANY_SECTOR_CARD = "noise_in_any_sector_card";
		public static final String SILENCE_CARD = "silence_card";
	}

	public static class ItemProperties {
		public static final int MAX_ITEM_CARDS = 12;
		public static final int MAX_ATTACK_ITEM_CARDS = 2;
		public static final int MAX_TELEPORT_ITEM_CARDS = 2;
		public static final int MAX_SEDATIVES_ITEM_CARDS = 3;
		public static final int MAX_SPOTLIGHT_ITEM_CARDS = 2;
		public static final int MAX_DEFENSE_ITEM_CARDS = 1;
		public static final int MAX_ADRENALINE_ITEM_CARDS = 2;

		public static final String ATTACK_ITEM_CARD = "attack_item";
		public static final String TELEPORT_ITEM_CARD = "teleport_item";
		public static final String SEDATIVES_ITEM_CARD = "sedatives_item";
		public static final String SPOTLIGHT_ITEM_CARD = "spotlight_item";
		public static final String DEFENSE_ITEM_CARD = "defense_item";
		public static final String ADRENALINE_ITEM_CARD = "adrenaline_item";
	}

	public static class EscapeHatchProperties {
		public static final int MAX_ESCAPE_HATCH_CARDS = 6;
		public static final int MAX_GREEN_ESCAPE_HATCH_CARDS = 3;
		public static final int MAX_RED_ESCAPE_HATCH_CARDS = 3;

		public static final String GREEN_ESCAPE_HATCH_CARD = "green_escape_hatch";
		public static final String RED_ESCAPE_HATCH_CARD = "red_escape_hatch";
	}
}
