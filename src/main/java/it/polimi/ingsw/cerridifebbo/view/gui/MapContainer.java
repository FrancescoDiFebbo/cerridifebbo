package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;

import java.awt.Color;
import java.awt.Container;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

/**
 * This class describes a map that contains sector buttons.It extends Container.
 * It uses the MapLayout
 * 
 * @see MapLayout
 * @author cerridifebbo
 *
 */
public class MapContainer extends Container {

	private static final long serialVersionUID = 1L;
	private transient LayoutManager mapLayout;
	private static final String PLAYER_STRING = "ME";
	private static final Color PLAYER_COLOR = new Color(201, 13, 239);
	private String precName;
	private Color precColor;
	private int precPosition;
	private boolean restore = false;

	/**
	 * This constructor creates the map container.It uses the
	 * SectorButtonFactory.
	 * 
	 * @see SectorButtonFactory
	 * @author cerridifebbo
	 * @param map
	 *            the map of the game
	 */
	public MapContainer(MapRemote map) {
		mapLayout = new MapLayout(MapRemote.ROWMAP, MapRemote.COLUMNMAP);
		SectorButtonFactory factory = new ConcreteSectorButtonFactory();
		for (int i = 0; i < MapRemote.ROWMAP; i++) {
			for (int j = 0; j < MapRemote.COLUMNMAP; j++) {
				SectorRemote temp = map.getCell(i, j);
				SectorButton button = factory.createSectorButton(temp);
				button.setBackground(Color.BLACK);
				add(button);
			}
		}

		setLayout(mapLayout);
		setSize(mapLayout.preferredLayoutSize(this));
	}

	/**
	 * This methods adds an action listener to the buttons of the map.
	 * 
	 * @author cerridifebbo
	 * @param moveListener
	 *            the actionListener that will be added to the sector buttons
	 */
	public void addListenersToButton(ActionListener moveListener) {
		int nComp = getComponentCount();
		SectorButton button;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				button = (SectorButton) getComponent(i);
				button.addActionListener(moveListener);
			}

	}

	/**
	 * This methods deletes an action listener to the buttons of the map.
	 * 
	 * @author cerridifebbo
	 * @param moveListener
	 *            the actionListener that will be deleted to the sector buttons
	 */
	public void deleteListenersToButton(ActionListener moveListener) {
		int nComp = getComponentCount();
		SectorButton button;
		for (int i = 0; i < nComp; i++)
			if (getComponent(i) != null) {
				button = (SectorButton) getComponent(i);
				button.removeActionListener(moveListener);
			}

	}

	/**
	 * This methods updates or initializes the position of the player.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just changed his position
	 */
	public void setPlayerPawn(PlayerRemote player) {
		if (restore) {
			getComponent(precPosition).setName(precName);
			getComponent(precPosition).setForeground(precColor);
		}
		String position = player.getPos();
		int nComp = getComponentCount();
		for (int i = 0; i < nComp; i++) {
			if (getComponent(i) != null
					&& getComponent(i).getName().equals(position)) {
				restore = true;
				precPosition = i;
				precName = getComponent(i).getName();
				precColor = getComponent(i).getForeground();
				getComponent(i).setForeground(PLAYER_COLOR);
				getComponent(i).setName(PLAYER_STRING);
			}
		}

	}

	/**
	 * This methods updates the status of the escape sector
	 * 
	 * @author cerridifebbo
	 * @param sector
	 *            the sector that has been changed
	 */
	public void updateEscapeHatchStatus(String sector) {
		int nComp = getComponentCount();
		for (int i = 0; i < nComp; i++) {
			if (getComponent(i) != null
					&& getComponent(i).getName().equals(sector.toString())) {
				getComponent(i).setForeground(
						EscapeHatchSectorButton.ESCAPE_HATCH_COLOR_KO);
			}
		}
	}
}
