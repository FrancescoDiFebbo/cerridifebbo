package it.polimi.ingsw.cerridifebbo.controller.common;

import java.io.IOException;

/**
 * The Interface ClientConnection provides methods to communicate to the
 * clients.
 *
 * @author cerridifebbo
 */
public interface ClientConnection {

	/**
	 * Sends message to client.
	 *
	 * @param message
	 *            the message
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void sendMessage(String message) throws IOException;

	/**
	 * Signals client to start turn.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void startTurn() throws IOException;

	/**
	 * Signals client to end turn.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void endTurn() throws IOException;

	/**
	 * Asks client for move.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void askForMove() throws IOException;

	/**
	 * Asks client for sector.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void askForSector() throws IOException;

	/**
	 * Asks client for card.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void askForCard() throws IOException;

	/**
	 * Sends game informations to the client.
	 *
	 * @param map
	 *            the map
	 * @param player
	 *            the player
	 * @param numberOfPlayers
	 *            the number of players
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void sendGameInformation(MapRemote map, PlayerRemote player, int numberOfPlayers) throws IOException;

	/**
	 * Sends player update to client.
	 *
	 * @param player
	 *            the player
	 * @param card
	 *            the card
	 * @param added
	 *            indicates if a card has been added or removed from player
	 *            deck.
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void sendPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added) throws IOException;

	/**
	 * Sends hatch update.
	 *
	 * @param map
	 *            the map
	 * @param sector
	 *            the sector
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void sendHatchUpdate(MapRemote map, SectorRemote sector) throws IOException;

	/**
	 * Disconnects user from server.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void disconnect() throws IOException;
}
