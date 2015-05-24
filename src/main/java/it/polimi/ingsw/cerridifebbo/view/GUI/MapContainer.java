package it.polimi.ingsw.cerridifebbo.view.GUI;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;

public class MapContainer extends Container {
	public MapContainer() {
		
		Map map = Map.getInstance();
		SectorButtonFactory factory = new ConcreteSectorButtonFactory();
		this.setForeground(Color.GREEN);
		for (int i = 0; i < Map.RAWMAP; i++) {
			for (int j = 0; j < Map.COLUMNMAP; j++) {
				Sector temp = map.getCell(i, j);
				String label = null;
				if (temp == null) {
					label = "";
				} else {
					label = temp.toString();
				}
				SectorButton button = factory.createSectorButton(temp, label);
				button.setBackground(Color.BLACK);
				this.add(button);
			}
		}
		LayoutManager mapLayout = new MapLayout(Map.RAWMAP, Map.COLUMNMAP);
		this.setLayout(mapLayout);
	}
}
