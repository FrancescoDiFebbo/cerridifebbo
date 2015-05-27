package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectorDeckFactory implements SingleTypeDeckFactory {

	public static final int MAX_NOISE_IN_YOUR_SECTOR_CARDS = 10;
	public static final int MAX_NOISE_IN_ANY_SECTOR_CARDS = 10;
	public static final int MAX_SILENCE_CARDS = 5;
	public static final int MAX_SECTOR_CARDS = MAX_NOISE_IN_YOUR_SECTOR_CARDS + MAX_NOISE_IN_ANY_SECTOR_CARDS + MAX_SILENCE_CARDS;
	public static final int MAX_SECTOR_ITEM_CARDS = 4;

	@Override
	public List<Card> createDeck(Integer numberOfPlayers) {
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < MAX_SILENCE_CARDS; i++) {
			cards.add(new SilenceCard());
		}

		for (int i = 0; i < MAX_NOISE_IN_YOUR_SECTOR_CARDS; i++) {
			if (i < MAX_SECTOR_ITEM_CARDS) {
				cards.add(new NoiseInSectorCard(true));
			} else {
				cards.add(new NoiseInSectorCard(false));
			}
		}

		for (int i = 0; i < MAX_NOISE_IN_ANY_SECTOR_CARDS; i++) {
			if (i < MAX_SECTOR_ITEM_CARDS) {
				cards.add(new NoiseAnySectorCard(true));
			} else {
				cards.add(new NoiseAnySectorCard(false));
			}
		}

		Collections.shuffle(cards);
		return cards;
	}
}
