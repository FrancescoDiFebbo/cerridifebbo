package it.polimi.ingsw.cerridifebbo.model;

/**
 * The Enum ItemCardName contains the names of item cards.
 *
 * @author cerridifebbo
 */
public enum ItemCardName {

	ADRENALINE("Adrenaline"), ATTACK("Attack"), DEFENSE("Defense"), SEDATIVES("Sedatives"), SPOTLIGHT("Spotlight"), TELEPORT("Teleport");

	/** The name of item. */
	private final String name;

	/**
	 * Instantiates a new item card name.
	 *
	 * @param name
	 *            the name
	 */
	ItemCardName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of item.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
}
