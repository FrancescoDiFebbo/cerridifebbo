package it.polimi.ingsw.cerridifebbo.model;

import java.util.List;

public interface SingleTypeDeckFactory {

	public List<Card> createDeck(Integer numberOfPlayers);

}
