package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.io.Serializable;

public class MapRemote implements Serializable {

	public static final int ROWMAP = Map.ROWMAP;
	public static final int COLUMNMAP = Map.COLUMNMAP;
	private static final long serialVersionUID = 1L;
	private final SectorRemote[][] mr = new SectorRemote[Map.ROWMAP][Map.COLUMNMAP];

	public MapRemote(Sector[][] map) {
		for (int i = 0; i < ROWMAP; i++) {
			for (int j = 0; j < COLUMNMAP; j++) {
				if (map[i][j] != null) {
					SectorRemote sr = new SectorRemote(map[i][j].getClass()
							.getSimpleName(), map[i][j].toString(),
							map[i][j].isPassable(), map[i][j].toString());
					mr[i][j] = sr;
				}
			}
		}
	}

	public SectorRemote getCell(int i, int j) {
		return mr[i][j];
	}

	public class SectorRemote implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final String type;
		private final String coordinate;
		private final boolean passable;
		private final String name;

		private SectorRemote(String type, String coordinate, boolean passable,
				String name) {
			this.type = type;
			this.coordinate = coordinate;
			this.passable = passable;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public String getCoordinate() {
			return coordinate;
		}

		public boolean isPassable() {
			return passable;
		}

	}
}
