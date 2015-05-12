package it.polimi.ingsw.cerridifebbo.model;

public interface DeckFactory {

	public Deck createDeck();
	public void setPlayers(int numberOfPlayers);

}
