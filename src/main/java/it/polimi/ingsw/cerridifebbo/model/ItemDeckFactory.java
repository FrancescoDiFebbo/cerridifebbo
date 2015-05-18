package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.Collections;

public class ItemDeckFactory implements SingleTypeDeckFactory {

	public static final int MAX_ATTACK_ITEM_CARDS = 2;
	public static final int MAX_TELEPORT_ITEM_CARDS = 2;
	public static final int MAX_SEDATIVES_ITEM_CARDS = 3;
	public static final int MAX_SPOTLIGHT_ITEM_CARDS = 2;
	public static final int MAX_DEFENSE_ITEM_CARDS = 1;
	public static final int MAX_ADRENALINE_ITEM_CARDS = 2;
	public static final int MAX_ITEM_CARDS = MAX_ATTACK_ITEM_CARDS + MAX_TELEPORT_ITEM_CARDS + MAX_SEDATIVES_ITEM_CARDS + MAX_SPOTLIGHT_ITEM_CARDS
			+ MAX_DEFENSE_ITEM_CARDS + MAX_ADRENALINE_ITEM_CARDS;

	@Override
	public ArrayList<Card> createDeck() {
		ArrayList<Card> cards = new ArrayList<Card>();
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
