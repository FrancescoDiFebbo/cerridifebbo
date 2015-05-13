package it.polimi.ingsw.cerridifebbo.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Map {

	private String name;
	private static Map instance=null;
	private  final Sector[][] grid;

	public static Map getInstance() {
		if (instance != null)
			return instance;
		File fileMap = new File(System.getProperty("user.dir")
				+ "//map//galilei.txt");
		return ConcreteMapFactory.createMap(fileMap);

	}
	
	Map (Sector[][] grid, String name){
		this.grid=grid;
		this.name=name;
	}
}
