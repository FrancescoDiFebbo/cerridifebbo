package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A factory for creating ItemDeck objects.
 * 
 * @author cerridifebbo
 */
public class ItemDeckFactory implements SingleTypeDeckFactory {

	/** The Constant MAX_ATTACK_ITEM_CARDS. */
	public static final int MAX_ATTACK_ITEM_CARDS = 2;

	/** The Constant MAX_TELEPORT_ITEM_CARDS. */
	public static final int MAX_TELEPORT_ITEM_CARDS = 2;

	/** The Constant MAX_SEDATIVES_ITEM_CARDS. */
	public static final int MAX_SEDATIVES_ITEM_CARDS = 3;

	/** The Constant MAX_SPOTLIGHT_ITEM_CARDS. */
	public static final int MAX_SPOTLIGHT_ITEM_CARDS = 2;

	/** The Constant MAX_DEFENSE_ITEM_CARDS. */
	public static final int MAX_DEFENSE_ITEM_CARDS = 1;

	/** The Constant MAX_ADRENALINE_ITEM_CARDS. */
	public static final int MAX_ADRENALINE_ITEM_CARDS = 2;

	/** The Constant MAX_ITEM_CARDS. */
	public static final int MAX_ITEM_CARDS = MAX_ATTACK_ITEM_CARDS + MAX_TELEPORT_ITEM_CARDS + MAX_SEDATIVES_ITEM_CARDS + MAX_SPOTLIGHT_ITEM_CARDS
			+ MAX_DEFENSE_ITEM_CARDS + MAX_ADRENALINE_ITEM_CARDS;

	/**
	 * This method creates a list of item cards.
	 * 
	 * @author cerridifebbo
	 * @param numberOfPlayers
	 *            null
	 * @return the list of item cards
	 */
	@Override
	public List<Card> createDeck(Integer numberOfPlayers) {
		List<Card> cards = new ArrayList<Card>();
		for (int i = 0; i < MAX_ATTACK_ITEM_CARDS; i++) {
			cards.add(new AttackItemCard());
		}
		for (int i = 0; i < MAX_TELEPORT_ITEM_CARDS; i++) {
			cards.add(new TeleportItemCard());
		}
		for (int i = 0; i < MAX_SEDATIVES_ITEM_CARDS; i++) {
			cards.add(new SedativesItemCard());
		}
		for (int i = 0; i < MAX_SPOTLIGHT_ITEM_CARDS; i++) {
			cards.add(new SpotlightItemCard());
		}
		for (int i = 0; i < MAX_DEFENSE_ITEM_CARDS; i++) {
			cards.add(new DefenseItemCard());
		}
		for (int i = 0; i < MAX_ADRENALINE_ITEM_CARDS; i++) {
			cards.add(new AdrenalineItemCard());
		}
		Collections.shuffle(cards);
		return cards;
	}

}
