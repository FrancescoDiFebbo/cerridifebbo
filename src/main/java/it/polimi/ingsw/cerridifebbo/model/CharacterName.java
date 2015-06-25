package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum CharacterName contains the names (human and aliens) of the players
 *
 * @author cerridifebbo
 */
public enum CharacterName {

	CAPTAIN("Capitano Ennio Maria Dominoni"), PILOT("Pilota Julia Niguloti a.k.a \"Cabal\""), PSYCHOLOGIST("Psicologo Silvano Porpora"), SOLDIER(
			"Soldato Tuccio Brandon a.k.a \"Piri\""), FIRST_ALIEN("Piero Ceccarella"), SECOND_ALIEN("Vittorio Martana"), THIRD_ALIEN("Maria Galbani"), FORTH_ALIEN(
			"Paolo Landon");

	/** The name of the player. */
	private final String name;

	/**
	 * Instantiates a new character name.
	 *
	 * @param name
	 *            the name
	 */
	private CharacterName(String name) {
		this.name = name;
	}

	/**
	 * Gets the human names.
	 *
	 * @return the human names
	 */
	public static List<String> getHumanNames() {
		List<String> names = new ArrayList<String>();
		names.add(CAPTAIN.name);
		names.add(PILOT.name);
		names.add(PSYCHOLOGIST.name);
		names.add(SOLDIER.name);
		return names;
	}

	/**
	 * Gets the alien names.
	 *
	 * @return the alien names
	 */
	public static List<String> getAlienNames() {
		List<String> names = new ArrayList<String>();
		names.add(FIRST_ALIEN.name);
		names.add(SECOND_ALIEN.name);
		names.add(THIRD_ALIEN.name);
		names.add(FORTH_ALIEN.name);
		return names;
	}
}
