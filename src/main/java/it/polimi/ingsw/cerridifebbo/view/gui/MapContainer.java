package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.view.gui.ConcreteSectorButtonFactory;

import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

public class MapContainer extends Container {

	private static final long serialVersionUID = 1L;
	private transient LayoutManager mapLayout;
	private static final String PLAYER_STRING = "ME";
	private static final Color PLAYER_COLOR = new Color(201, 13, 239);
	private String precName;
	private Color precColor;
	private int precPosition;
	private boolean restore = false;

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

	public void addListenersToButton(ActionListener moveListener) {
		int nComp = getComponentCount();
		SectorButton button;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				button = (SectorButton) getComponent(i);
				button.addActionListener(moveListener);
			}

	}

	public void deleteListenersToButton(ActionListener moveListener) {
		int nComp = getComponentCount();
		SectorButton button;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				button = (SectorButton) getComponent(i);
				button.removeActionListener(moveListener);
			}

	}

	public void setPlayerPawn(Player player) {
		if (restore) {
			getComponent(precPosition).setName(precName);
			getComponent(precPosition).setForeground(precColor);
		}
		Sector position = player.getPosition();
		int nComp = getComponentCount();
		for (int i = 0; i < nComp; i++) {
			if (getComponent(i) != null
					&& getComponent(i).getName().equals(position.toString())) {
				restore = true;
				precPosition = i;
				precName = getComponent(i).getName();
				precColor = getComponent(i).getForeground();
				getComponent(i).setForeground(PLAYER_COLOR);
				getComponent(i).setName(PLAYER_STRING);
			}
		}

	}
}
