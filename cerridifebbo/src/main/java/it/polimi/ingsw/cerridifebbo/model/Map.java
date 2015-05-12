package it.polimi.ingsw.cerridifebbo.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Map {

	private String name;
	public static final int COLUMNMAP = 23;
	public static final int RAWMAP = 14;

	private Sector[][] grid = new Sector[RAWMAP][COLUMNMAP];

	public void createMap(File mapFile) {

		try {
			SectorFactory factory = new ConcreteSectorFactory();
			Scanner fr = new Scanner(mapFile);
			name = mapFile.getName();
			for (int i = 0; i < RAWMAP; i++) {
				for (int j = 0; j < COLUMNMAP; j++) {
					grid[i][j] = factory.createSector(fr.nextInt(), i, j);
				}
			}
			fr.close();
			for (int j = 0; j < COLUMNMAP; j++) {
				for (int i = 0; i < RAWMAP; i++) {
					if (grid[i][j] != null) {
						setAdjacentSector(i, j);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		Player test = new Player();
		test.setMaxMovement(3);
		test.setPos(grid[5][5]);
		System.out.println("val " + grid[7][9].isReachable(test));
	}

	private void setAdjacentSector(int raw, int column) {
		setNorthEast(raw, column);
		setSouthEast(raw, column);
		setSouth(raw, column);
	}

	private void setNorthEast(int raw, int column) {
		if (column % 2 == 0) {
			if (raw - 1 >= 0 && column + 1 < COLUMNMAP
					&& grid[raw - 1][column + 1] != null) {
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

	private void setSouthEast(int raw, int column) {
		if (column % 2 == 0) {
			if (column + 1 < COLUMNMAP && grid[raw][column + 1] != null) {
				grid[raw][column].setSouthEast(grid[raw][column + 1]);
				grid[raw][column + 1].setNorthWest(grid[raw][column]);
			} else {
				grid[raw][column].setSouthEast(null);
			}
		} else {
			if (column + 1 < COLUMNMAP && raw + 1 < RAWMAP
					&& grid[raw + 1][column + 1] != null) {
				grid[raw][column].setSouthEast(grid[raw + 1][column + 1]);
				grid[raw + 1][column + 1].setNorthWest(grid[raw][column]);
			} else {
				grid[raw][column].setSouthEast(null);
			}
		}
	}

	private void setSouth(int raw, int column) {
		if (raw + 1 < RAWMAP && grid[raw + 1][column] != null) {
			grid[raw][column].setSouth(grid[raw + 1][column]);
			grid[raw + 1][column].setNorth(grid[raw][column]);
		} else {
			grid[raw][column].setSouth(null);
		}
	}

	public static void main(String[] args) {
		File a = new File(
				"C://Users//stefano//git//cerridifebbo//cerridifebbo//map//galilei.txt");
		Map ao = new Map();
		ao.createMap(a);

	}

}
