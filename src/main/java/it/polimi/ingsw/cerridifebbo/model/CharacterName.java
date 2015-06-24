package it.polimi.ingsw.cerridifebbo.model;

import java.util.ArrayList;
import java.util.List;

public enum CharacterName {

	CAPTAIN("Capitano Ennio Maria Dominoni"), PILOT("Pilota Julia Niguloti a.k.a \"Cabal\""), PSYCHOLOGIST("Psicologo Silvano Porpora"), SOLDIER(
			"Soldato Tuccio Brandon a.k.a \"Piri\""), FIRST_ALIEN("Piero Ceccarella"), SECOND_ALIEN("Vittorio Martana"), THIRD_ALIEN("Maria Galbani"), FORTH_ALIEN(
			"Paolo Landon");

	private final String name;

	private CharacterName(String name) {
		this.name = name;
	}

	public static List<String> getHumanNames() {
		List<String> names = new ArrayList<String>();
		names.add(CAPTAIN.name);
		names.add(PILOT.name);
		names.add(PSYCHOLOGIST.name);
		names.add(SOLDIER.name);
		return names;
	}
	
	public static List<String> getAlienNames() {
		List<String> names = new ArrayList<String>();
		names.add(FIRST_ALIEN.name);
		names.add(SECOND_ALIEN.name);
		names.add(THIRD_ALIEN.name);
		names.add(FORTH_ALIEN.name);
		return names;
	}
}
