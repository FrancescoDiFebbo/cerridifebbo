package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class describes a sector deck factory. It implements
 * SingleTypeDeckFactory.
 * 
 * @see SingleTypeDeckFactory
 * 
 * @author cerridifebbo
 *
 */
public class SectorDeckFactory implements SingleTypeDeckFactory {

	public static final int MAX_NOISE_IN_YOUR_SECTOR_CARDS = 10;
	public static final int MAX_NOISE_IN_ANY_SECTOR_CARDS = 10;
	public static final int MAX_SILENCE_CARDS = 5;
	public static final int MAX_SECTOR_CARDS = MAX_NOISE_IN_YOUR_SECTOR_CARDS + MAX_NOISE_IN_ANY_SECTOR_CARDS + MAX_SILENCE_CARDS;
	public static final int MAX_SECTOR_ITEM_CARDS = 4;

	/**
	 * This method creates a list of sector cards.
	 * 
	 * @author cerridifebbo
	 * @param numberOfPlayers
	 *            null
	 * @return the list of sector cards
	 */
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
