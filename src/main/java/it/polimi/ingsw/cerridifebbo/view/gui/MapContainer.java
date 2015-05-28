package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;

public class MapContainer extends Container {

	private static final long serialVersionUID = 1L;
	private LayoutManager mapLayout;

	public MapContainer(Map map) {
		mapLayout = new MapLayout(Map.ROWMAP, Map.COLUMNMAP);
		SectorButtonFactory factory = new ConcreteSectorButtonFactory();
		setForeground(GUIGraphics.FOREGROUND_COLOR);
		for (int i = 0; i < Map.ROWMAP; i++) {
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
				add(button);
			}
		}

		setLayout(mapLayout);
		setSize(mapLayout.preferredLayoutSize(this));
	}

	public void addListenersToButton() {
		int nComp = getComponentCount();
		SectorButton button;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				button = (SectorButton) getComponent(i);
				button.addActionListener(button);
			}

	}

	public void deleteListenersToButton() {
		int nComp = getComponentCount();
		SectorButton button;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				button = (SectorButton) getComponent(i);
				button.removeActionListener(button);
			}

	}

}
