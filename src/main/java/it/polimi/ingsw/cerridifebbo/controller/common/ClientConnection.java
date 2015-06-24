package it.polimi.ingsw.cerridifebbo.controller.common;

import java.io.IOException;

public interface ClientConnection {

	public void sendMessage(String message) throws IOException;

	public void startTurn() throws IOException;

	public void endTurn() throws IOException;

	public void askForMove() throws IOException;

	public void askForSector() throws IOException;

	public void askForCard() throws IOException;

	public void sendGameInformation(MapRemote map, PlayerRemote player, int size) throws IOException;

	public void sendPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added) throws IOException;

	public void sendHatchUpdate(MapRemote map, SectorRemote sector) throws IOException;

	public void disconnect() throws IOException;
}
