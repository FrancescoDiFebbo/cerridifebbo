package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote.SectorRemote;

/**
 * This abstract class describes a generic graphics that is used to show the
 * game to the client.
 * 
 * @author cerridifebbo
 *
 */

public abstract class Graphics {

	private NetworkInterface network;
	protected boolean initialized;

	/**
	 * getter of initialized
	 */
	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * This method initializes the graphics.
	 * 
	 * @author cerridifebbo
	 * @param map
	 *            the map of the specific game
	 * @param player
	 *            the player used by the client
	 * @param numberOfPlayers
	 *            the number of player of the specific game
	 */
	public abstract void initialize(MapRemote map, PlayerRemote player,
			int numberOfPlayers);

	/**
	 * This method show into the graphics the parameter message.
	 * 
	 * @author cerridifebbo
	 * @param message
	 *            the message that will be displayed
	 */
	public abstract void sendMessage(String message);

	/**
	 * This method prepare all the staff for start the player's turn.
	 * 
	 * @author cerridifebbo
	 */
	public abstract void startTurn();

	/**
	 * This method prepare all the staff for end the player's turn.
	 * 
	 * @author cerridifebbo
	 */
	public abstract void endTurn();

	/**
	 * setter of network
	 */
	public void setNetworkInterface(NetworkInterface network) {
		this.network = network;
	}

	/**
	 * getter of network
	 */
	public NetworkInterface getNetworkInterface() {
		return network;
	}

	/**
	 * This method prepare all the staff for prepare the player to declare a
	 * move.
	 * 
	 * @author cerridifebbo
	 */
	public abstract void declareMove();

	/**
	 * This method prepare all the staff for prepare the player to declare a
	 * sector.
	 * 
	 * @author cerridifebbo
	 */
	public abstract void declareSector();

	/**
	 * This method update the position of the player on the map.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that have just changed the position
	 */
	public abstract void updatePlayerPosition(PlayerRemote player);

	/**
	 * This method prepare all the staff for prepare the player to declare a
	 * card.
	 * 
	 * @author cerridifebbo
	 */
	public abstract void declareCard();

	/**
	 * This method update the cards of the player. It deletes the card parameter
	 * of the player parameter.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just deleted a card
	 * @param card
	 *            the card that has just been deleted
	 */
	public abstract void deletePlayerCard(PlayerRemote player, Card card);

	/**
	 * This method update the cards of the player. It adds the card parameter of
	 * the player parameter.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just added a card
	 * @param card
	 *            the card that has just been added
	 */
	public abstract void addPlayerCard(PlayerRemote player, Card card);

	/**
	 * This method update the status of the parameter sector of the parameter
	 * map.
	 * 
	 * @author cerridifebbo
	 * @param map
	 *            the current map of the game
	 * @param sector
	 *            the sector that has just been modified
	 */
	public abstract void updateEscapeHatch(MapRemote map, SectorRemote sector);
}
