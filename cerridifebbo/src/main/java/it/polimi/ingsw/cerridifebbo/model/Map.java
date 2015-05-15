package it.polimi.ingsw.cerridifebbo.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Map {
	private static final int COLUMNMAP = 23;
	private static final int RAWMAP = 14;
	public static final Sector[][] grid = new Sector[RAWMAP][COLUMNMAP];
	private static Map instance = new Map(new File(System.getProperty("user.dir") + "//map//galilei.txt"));

	public static Map getInstance() {
		return instance;

	}

	private Map(File mapFile) {
		try {
			SectorFactory factory = new ConcreteSectorFactory();
			Scanner fr = new Scanner(mapFile);
			for (int i = 0; i < RAWMAP; i++) {
				for (int j = 0; j < COLUMNMAP; j++) {
					grid[i][j] = factory.createSector(fr.nextInt(), i, j);
				}
			}
			fr.close();
			for (int j = 0; j < COLUMNMAP; j++) {
				for (int i = 0; i < RAWMAP; i++) {
					if (grid[i][j] != null) {
						setAdjacentSector(i, j, grid);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void setAdjacentSector(int raw, int column, Sector[][] grid) {
		setNorthEast(raw, column, grid);
		setSouthEast(raw, column, grid);
		setSouth(raw, column, grid);
	}

	private static void setNorthEast(int raw, int column, Sector[][] grid) {
		if (column % 2 == 0) {
			if (raw - 1 >= 0 && column + 1 < COLUMNMAP && grid[raw - 1][column + 1] != null) {
				grid[raw][column].setNorthEast(grid[raw - 1][column + 1]);
				grid[raw - 1][column + 1].setSouthWest(grid[raw][column]);
			} else {
				grid[raw][column].setNorthEast(null);
			}
		} else {
			if (column + 1 < COLUMNMAP && grid[raw][column + 1] != null) {
				grid[raw][column].setNorthEast(grid[raw][column + 1]);
				grid[raw][column + 1].setSouthWest(grid[raw][column]);
			} else {
				grid[raw][column].setNorthEast(null);
			}
		}
	}

	private static void setSouthEast(int raw, int column, Sector[][] grid) {
		if (column % 2 == 0) {
			if (column + 1 < COLUMNMAP && grid[raw][column + 1] != null) {
				grid[raw][column].setSouthEast(grid[raw][column + 1]);
				grid[raw][column + 1].setNorthWest(grid[raw][column]);
			} else {
				grid[raw][column].setSouthEast(null);
			}
		} else {
			if (column + 1 < COLUMNMAP && raw + 1 < RAWMAP && grid[raw + 1][column + 1] != null) {
				grid[raw][column].setSouthEast(grid[raw + 1][column + 1]);
				grid[raw + 1][column + 1].setNorthWest(grid[raw][column]);
			} else {
				grid[raw][column].setSouthEast(null);
			}
		}
	}

	private static void setSouth(int raw, int column, Sector[][] grid) {
		if (raw + 1 < RAWMAP && grid[raw + 1][column] != null) {
			grid[raw][column].setSouth(grid[raw + 1][column]);
			grid[raw + 1][column].setNorth(grid[raw][column]);
		} else {
			grid[raw][column].setSouth(null);
		}
	}

	public Sector getRandomHumanSector() {
		ArrayList<Sector> humanSectors = new ArrayList<Sector>();
		for (int i = 0; i < RAWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (grid[i][j] instanceof HumanSector) {
					humanSectors.add(grid[i][j]);
				}
			}
		}
		int index = new Random().nextInt(humanSectors.size());
		return humanSectors.get(index);
	}

	public Sector getRandomAlienSector() {
		ArrayList<Sector> alienSectors = new ArrayList<Sector>();
		for (int i = 0; i < RAWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (grid[i][j] instanceof AlienSector) {
					alienSectors.add(grid[i][j]);
				}
			}
		}
		int index = new Random().nextInt(alienSectors.size());
		return alienSectors.get(index);
	}
}
