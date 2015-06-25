package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;

/**
 * Provides the fundamental methods for network interfaces.
 * 
 * @author cerridifebbo
 */
public interface NetworkInterface {

	/**
	 * Opens the connection. Allows the client to connect to the server.
	 */
	public void connect();

	/**
	 * Closes the connection.
	 */
	public void close();

	/**
	 * Registers client on server.
	 *
	 * @return true, if successful
	 */
	public boolean registerClientOnServer();

	/**
	 * Sends to server a couple of strings. "action" describes the command,
	 * "target" describes the value.
	 *
	 * @param action
	 *            the command to be performed by the server
	 * @param target
	 *            the value used by server
	 */
	public void sendToServer(String action, String target);

	/**
	 * Sets the graphic interface chose by user.
	 *
	 * @param graphics
	 *            the graphic interface
	 */
	public void setGraphicInterface(Graphics graphics);

	/**
	 * Sets the game information coming from server. It initializes the UI.
	 *
	 * @param map
	 *            the map
	 * @param player
	 *            the player
	 * @param numberOfPlayers
	 *            the number of players
	 */
	public void setGameInformation(MapRemote map, PlayerRemote player, int numberOfPlayers);

	/**
	 * Sets the player update.
	 *
	 * @param player
	 *            the player
	 * @param card
	 *            the card
	 * @param added
	 *            describes if the card is added or removed from player's deck.
	 */
	public void setPlayerUpdate(PlayerRemote player, ItemCardRemote card, boolean added);

	/**
	 * Sets the hatch update.
	 *
	 * @param map
	 *            the map
	 * @param sector
	 *            the hatch sector to be updated.
	 */
	public void setHatchUpdate(MapRemote map, SectorRemote sector);
}
