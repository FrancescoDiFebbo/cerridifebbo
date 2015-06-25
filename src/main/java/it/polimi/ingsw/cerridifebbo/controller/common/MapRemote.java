package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.io.Serializable;

/**
 * The Class MapRemote is a lightweight class for sharing from server to client.
 *
 * @author cerridifebbo
 * @see Map
 */
public class MapRemote implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The Constant ROWMAP. */
	public static final int ROWMAP = Map.ROWMAP;

	/** The Constant COLUMNMAP. */
	public static final int COLUMNMAP = Map.COLUMNMAP;

	/** The grid containing SectorRemote. */
	private final SectorRemote[][] grid = new SectorRemote[Map.ROWMAP][Map.COLUMNMAP];

	/**
	 * Instantiates a new map remote from Map.
	 *
	 * @param map
	 *            the map
	 */
	public MapRemote(Sector[][] map) {
		for (int i = 0; i < ROWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (map[i][j] != null) {
					SectorRemote sr = new SectorRemote(map[i][j]);
					grid[i][j] = sr;
				}
			}
		}
	}

	/**
	 * Gets the sector from indexes.
	 *
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the cell
	 */
	public SectorRemote getCell(int i, int j) {
		return grid[i][j];
	}
}
