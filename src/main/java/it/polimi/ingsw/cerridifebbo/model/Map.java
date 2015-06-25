package it.polimi.ingsw.cerridifebbo.model;

import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class describes a map.The map is created through a file of integer.
 * 
 * @author cerridifebbo
 * @see ItemCard that is extended by this class.
 */

public class Map implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(Map.class.getName());

	public static final int COLUMNMAP = 23;
	public static final int ROWMAP = 14;
	private static final Sector[][] grid = new Sector[ROWMAP][COLUMNMAP];
	private static Map instance = new Map(new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "file"
			+ System.getProperty("file.separator") + "galilei.txt"));
	private static MapRemote mapRemote;

	/**
	 * With this constructor the map is created. It can't be invoked by outside
	 * user. If someone wants to receive a new ma, he has to invoke the
	 * singleton method.This method is invoked by instance
	 * 
	 * @see instance
	 * 
	 * @author cerridifebbo
	 * @param mapFile
	 *            the file with the specific. Must be formed by integer.
	 * @see ConcreteSectorFactory for the constraints about the type of sector.
	 * @exception IOException
	 *                throws a IOEXception when mapFile is not valid
	 */
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

	/**
	 * eager singleton.
	 * 
	 * @author cerridifebbo
	 * @return the instance of the map
	 */
	public static Map getInstance() {
		return instance;

	}

	/**
	 * With this method the sector of the map is setting with his adjacent
	 * sector.
	 * 
	 * @author cerridifebbo
	 * @param row
	 *            the row of the sector
	 * @param column
	 *            the column of the sector
	 * @param grid
	 *            the map grid map
	 */
	private static void setAdjacentSector(int row, int column, Sector[][] grid) {
		if (grid[row][column] == null) {
			return;
		}
		setNorthEast(row, column, grid);
		setSouthEast(row, column, grid);
		setSouth(row, column, grid);
	}

	/**
	 * This method set the northEast adjacent sector of the specific sector.
	 * 
	 * @see setAdjacentSector who invokes this method.
	 * @author cerridifebbo
	 * @param row
	 *            the row of the sector
	 * @param column
	 *            the column of the sector
	 * @param grid
	 *            the map grid map
	 */
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

	/**
	 * This method set the southEast adjacent sector of the specific sector.
	 * 
	 * @see setAdjacentSector who invokes this method.
	 * @author cerridifebbo
	 * @param row
	 *            the row of the sector
	 * @param column
	 *            the column of the sector
	 * @param grid
	 *            the map grid map
	 */
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

	/**
	 * This method set the south adjacent sector of the specific sector.
	 * 
	 * @see setAdjacentSector who invokes this method.
	 * @author cerridifebbo
	 * @param row
	 *            the row of the sector
	 * @param column
	 *            the column of the sector
	 * @param grid
	 *            the map grid map
	 */
	private static void setSouth(int row, int column, Sector[][] grid) {
		if (row + 1 < ROWMAP && grid[row + 1][column] != null) {
			grid[row][column].setSouth(grid[row + 1][column]);
			grid[row + 1][column].setNorth(grid[row][column]);
		} else {
			grid[row][column].setSouth(null);
		}
	}

	/**
	 * This method return the cell in position row i and column j
	 * 
	 * @author cerridifebbo
	 * @param i
	 *            the row
	 * @param j
	 *            the column
	 * @return the sector in position row i and column j
	 */
	public Sector getCell(int i, int j) {
		return grid[i][j];
	}

	/**
	 * This method return the cell that has the same name of the parameter
	 * 
	 * @author cerridifebbo
	 * @param sector
	 *            the name of the sector
	 * @return the sector that has the same name of the parameter.Return null if
	 *         sector is not found.
	 */
	public Sector getCell(String sector) {
		if (sector == null) {
			return null;
		}
		for (int i = 0; i < ROWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (grid[i][j] != null && grid[i][j].toString().equalsIgnoreCase(sector)) {
					return grid[i][j];
				}
			}
		}
		return null;
	}

	/**
	 * This method return the sector that is instance of HumanSector
	 * 
	 * @author cerridifebbo
	 * @return the sector that is instance of HumanSector. Return null if sector
	 *         is not found.
	 */
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

	/**
	 * This method return the sector that is instance of AlienSector
	 * 
	 * @author cerridifebbo
	 * @return the sector that is instance of AlienSector. Return null if sector
	 *         is not found.
	 */
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

	/**
	 * This method return a list of sector that is instance of EscapeHatchSector
	 * 
	 * @author cerridifebbo
	 * @return the list of sector that is instance of EscapeHatchSector.
	 */
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

	/**
	 * This method return a mapRemote.
	 * 
	 * @author cerridifebbo
	 * @return a MapRemote object
	 */
	public MapRemote getMapRemote() {
		if (mapRemote == null) {
			mapRemote = new MapRemote(grid);
			return mapRemote;
		}
		return mapRemote;
	}
}
