package it.polimi.ingsw.cerridifebbo.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Map implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789290281145148337L;

	private static final Logger LOG = Logger.getLogger(Map.class.getName());

	public static final int COLUMNMAP = 23;
	public static final int ROWMAP = 14;
	private static final Sector[][] grid = new Sector[ROWMAP][COLUMNMAP];
	private static Map instance = new Map(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "map"
			+ System.getProperty("file.separator") + "galilei.txt"));

	private Map(File mapFile) {
		try {
			SectorFactory factory = new ConcreteSectorFactory();
			Scanner fr = new Scanner(mapFile);
			for (int i = 0; i < ROWMAP; i++) {
				for (int j = 0; j < COLUMNMAP; j++) {
					grid[i][j] = factory.createSector(fr.nextInt(), i, j);
				}
			}
			fr.close();
			for (int j = 0; j < COLUMNMAP; j++) {
				for (int i = 0; i < ROWMAP; i++) {
					setAdjacentSector(i, j, grid);
				}
			}

		} catch (IOException e) {
			LOG.log(Level.WARNING, e.getMessage(), e);
		}
	}

	public static Map getInstance() {
		return instance;

	}

	private static void setAdjacentSector(int row, int column, Sector[][] grid) {
		if (grid[row][column] == null) {
			return;
		}
		setNorthEast(row, column, grid);
		setSouthEast(row, column, grid);
		setSouth(row, column, grid);
	}

	private static void setNorthEast(int row, int column, Sector[][] grid) {
		if (column % 2 == 0) {
			if (row - 1 >= 0 && column + 1 < COLUMNMAP && grid[row - 1][column + 1] != null) {
				grid[row][column].setNorthEast(grid[row - 1][column + 1]);
				grid[row - 1][column + 1].setSouthWest(grid[row][column]);
			} else {
				grid[row][column].setNorthEast(null);
			}
		} else {
			if (column + 1 < COLUMNMAP && grid[row][column + 1] != null) {
				grid[row][column].setNorthEast(grid[row][column + 1]);
				grid[row][column + 1].setSouthWest(grid[row][column]);
			} else {
				grid[row][column].setNorthEast(null);
			}
		}
	}

	private static void setSouthEast(int row, int column, Sector[][] grid) {
		if (column % 2 == 0) {
			if (column + 1 < COLUMNMAP && grid[row][column + 1] != null) {
				grid[row][column].setSouthEast(grid[row][column + 1]);
				grid[row][column + 1].setNorthWest(grid[row][column]);
			} else {
				grid[row][column].setSouthEast(null);
			}
		} else {
			if (column + 1 < COLUMNMAP && row + 1 < ROWMAP && grid[row + 1][column + 1] != null) {
				grid[row][column].setSouthEast(grid[row + 1][column + 1]);
				grid[row + 1][column + 1].setNorthWest(grid[row][column]);
			} else {
				grid[row][column].setSouthEast(null);
			}
		}
	}

	private static void setSouth(int row, int column, Sector[][] grid) {
		if (row + 1 < ROWMAP && grid[row + 1][column] != null) {
			grid[row][column].setSouth(grid[row + 1][column]);
			grid[row + 1][column].setNorth(grid[row][column]);
		} else {
			grid[row][column].setSouth(null);
		}
	}

	public Sector getCell(int i, int j) {
		return grid[i][j];
	}

	public Sector getCell(String sector) {
		for (int i = 0; i < ROWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (grid[i][j] != null && grid[i][j].toString().equalsIgnoreCase(sector)) {
					return grid[i][j];
				}
			}
		}
		return null;
	}

	public Sector getHumanSector() {
		for (int i = 0; i < ROWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (grid[i][j] instanceof HumanSector) {
					return grid[i][j];
				}
			}
		}
		return null;
	}

	public Sector getAlienSector() {
		for (int i = 0; i < ROWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (grid[i][j] instanceof AlienSector) {
					return grid[i][j];
				}
			}
		}
		return null;
	}

	public List<Sector> getEscapeHatchSectors() {
		List<Sector> hatches = new ArrayList<Sector>();
		for (int i = 0; i < ROWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (grid[i][j] instanceof EscapeHatchSector) {
					hatches.add(grid[i][j]);
				}
			}
		}
		return hatches;
	}
}
