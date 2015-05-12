package it.polimi.ingsw.cerridifebbo.model;

import java.util.Collections;
import java.util.Vector;

public class ConcreteDeckFactory implements DeckFactory {

	int numberOfPlayers = 0;

	public Deck createDeck() {
		if (numberOfPlayers == 0) {
			throw new IllegalStateException("Call setPlayers() before calling this");
		}
		return getDeck();
	}

	public void setPlayers(int numberOfPlayers) {
		if (numberOfPlayers < CharacterInfo.MIN_PLAYERS || numberOfPlayers > CharacterInfo.MAX_PLAYERS) {
			throw new IllegalArgumentException("Number of players between " + CharacterInfo.MIN_PLAYERS + " and " + CharacterInfo.MAX_PLAYERS);
		}
		this.numberOfPlayers = numberOfPlayers;
	}

	private Deck getDeck() {
		Vector<Card> characterCards = createCharactersDeck();
		Vector<Card> sectorCards = createSectorsDeck();
		Vector<Card> itemCards = createItemsDeck();
		Vector<Card> escapeHatchCards = createEscapeHatchesDeck();

		return new Deck(characterCards, sectorCards, itemCards, escapeHatchCards);
	}

	private Vector<Card> createCharactersDeck() {
		Vector<Card> cards = new Vector<Card>();
		for (int i = 0; i < numberOfPlayers / 2; i++) {
			cards.add(new HumanCard(CharacterInfo.HUMAN_NAMES[i]));
		}
		if (numberOfPlayers % 2 == 0) {
			for (int i = 0; i < numberOfPlayers / 2; i++) {
				cards.add(new AlienCard(CharacterInfo.ALIEN_NAMES[i]));
			}
		} else {
			for (int i = 0; i < numberOfPlayers / 2 + 1; i++) {
				cards.add(new AlienCard(CharacterInfo.ALIEN_NAMES[i]));
			}
		}
		Collections.shuffle(cards);
		return cards;
	}

	private Vector<Card> createSectorsDeck() {
		// TODO migliorare il codice
		Vector<Card> cards = new Vector<Card>();
		for (int i = 0; i < SectorInfo.MAX_SILENCE_CARDS; i++) {
			cards.add(new SilenceCard());
		}

		for (int i = 0; i < SectorInfo.MAX_NOISE_IN_YOUR_SECTOR_CARDS; i++) {
			if (i < SectorInfo.MAX_SECTOR_ITEM_CARDS) {
				cards.add(new NoiseInSectorCard(true));
			} else {
				cards.add(new NoiseInSectorCard(false));
			}
		}

		for (int i = 0; i < SectorInfo.MAX_NOISE_IN_ANY_SECTOR_CARDS; i++) {
			if (i < SectorInfo.MAX_SECTOR_ITEM_CARDS) {
				cards.add(new NoiseAnySectorCard(true));
			} else {
				cards.add(new NoiseAnySectorCard(false));
			}
		}

		Collections.shuffle(cards);
		return cards;
	}

	private Vector<Card> createItemsDeck() {
		// TODO
		Vector<Card> cards = new Vector<Card>();
		for (int i = 0; i < ItemInfo.MAX_ATTACK_ITEM_CARDS; i++) {
			cards.add(new AttackItemCard());
		}
		for (int i = 0; i < ItemInfo.MAX_TELEPORT_ITEM_CARDS; i++) {
			cards.add(new TeleportItemCard());
		}
		for (int i = 0; i < ItemInfo.MAX_SEDATIVES_ITEM_CARDS; i++) {
			cards.add(new SedativesItemCard());
		}
		for (int i = 0; i < ItemInfo.MAX_SPOTLIGHT_ITEM_CARDS; i++) {
			cards.add(new SpotlightItemCard());
		}
		for (int i = 0; i < ItemInfo.MAX_DEFENSE_ITEM_CARDS; i++) {
			cards.add(new DefenseItemCard());
		}
		for (int i = 0; i < ItemInfo.MAX_ADRENALINE_ITEM_CARDS; i++) {
			cards.add(new AdrenalineItemCard());
		}
		Collections.shuffle(cards);
		return cards;

	}

	private Vector<Card> createEscapeHatchesDeck() {
		Vector<Card> cards = new Vector<Card>();
		for (int i = 0; i < EscapeHatchInfo.MAX_GREEN_ESCAPE_HATCH_CARDS; i++) {
			cards.add(new GreenEscapeHatchCard());
		}
		for (int i = 0; i < EscapeHatchInfo.MAX_RED_ESCAPE_HATCH_CARDS; i++) {
			cards.add(new RedEscapeHatchCard());
		}
		Collections.shuffle(cards);
		return cards;
	}

	private static class CharacterInfo {
		public static final int MAX_HUMAN_CARDS = 4;
		public static final int MAX_ALIEN_CARDS = 4;
		public static final int MAX_PLAYERS = MAX_HUMAN_CARDS + MAX_ALIEN_CARDS;
		public static final int MIN_PLAYERS = 2;

		public static final String[] HUMAN_NAMES = { "Capitano: Ennio Maria Dominoni", "Pilota: Julia Niguloti a.k.a \"Cabal\"",
				"Psicologo: Silvano Porpora", "Soldato: Tuccio Brandon a.k.a \"Piri\"" };
		public static final String[] ALIEN_NAMES = { "Piero Ceccarella", "Vittorio Martana", "Maria Galbani", "Paolo Landon" };
	}

	private static class SectorInfo {
		public static final int MAX_NOISE_IN_YOUR_SECTOR_CARDS = 10;
		public static final int MAX_NOISE_IN_ANY_SECTOR_CARDS = 10;
		public static final int MAX_SILENCE_CARDS = 5;
		public static final int MAX_SECTOR_CARDS = MAX_NOISE_IN_YOUR_SECTOR_CARDS + MAX_NOISE_IN_ANY_SECTOR_CARDS + MAX_SILENCE_CARDS;
		public static final int MAX_SECTOR_ITEM_CARDS = 4;

	}

	private static class ItemInfo {
		public static final int MAX_ATTACK_ITEM_CARDS = 2;
		public static final int MAX_TELEPORT_ITEM_CARDS = 2;
		public static final int MAX_SEDATIVES_ITEM_CARDS = 3;
		public static final int MAX_SPOTLIGHT_ITEM_CARDS = 2;
		public static final int MAX_DEFENSE_ITEM_CARDS = 1;
		public static final int MAX_ADRENALINE_ITEM_CARDS = 2;
		public static final int MAX_ITEM_CARDS = MAX_ATTACK_ITEM_CARDS + MAX_TELEPORT_ITEM_CARDS + MAX_SEDATIVES_ITEM_CARDS
				+ MAX_SPOTLIGHT_ITEM_CARDS + MAX_DEFENSE_ITEM_CARDS + MAX_ADRENALINE_ITEM_CARDS;
	}

	private static class EscapeHatchInfo {
		public static final int MAX_GREEN_ESCAPE_HATCH_CARDS = 3;
		public static final int MAX_RED_ESCAPE_HATCH_CARDS = 3;
		public static final int MAX_ESCAPE_HATCH_CARDS = MAX_GREEN_ESCAPE_HATCH_CARDS + MAX_RED_ESCAPE_HATCH_CARDS;

	}
}
