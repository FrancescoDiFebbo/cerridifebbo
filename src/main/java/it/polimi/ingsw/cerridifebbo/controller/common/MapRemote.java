package it.polimi.ingsw.cerridifebbo.controller.common;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.io.Serializable;

public class MapRemote implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final SectorRemote[][] mr = new SectorRemote[Map.ROWMAP][Map.COLUMNMAP];

	public MapRemote(Sector[][] map) {
		for (int i = 0; i < Map.ROWMAP; i++) {
			for (int j = 0; j < Map.COLUMNMAP; j++) {
				if (map[i][j] != null) {
					SectorRemote sr = new SectorRemote(map[i][j].getClass().getSimpleName(), map[i][j].toString());
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

		private SectorRemote(String type, String coordinate) {
			this.type = type;
			this.coordinate = coordinate;
		}

		public String getType() {
			return type;
		}

		public String getCoordinate() {
			return coordinate;
		}
	}
}
