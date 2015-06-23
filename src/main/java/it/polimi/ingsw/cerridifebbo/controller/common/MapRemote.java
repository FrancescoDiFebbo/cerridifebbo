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
					SectorRemote sr = new SectorRemote(map[i][j]);
					mr[i][j] = sr;
				}
			}
		}
	}

	public SectorRemote getCell(int i, int j) {
		return mr[i][j];
	}
}
