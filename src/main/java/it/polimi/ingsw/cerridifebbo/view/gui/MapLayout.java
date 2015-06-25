package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.*;
import java.io.Serializable;

/**
 * This class is a custom layout. It puts the elements of the container in a
 * hexagonal grid.
 * 
 * @see LayoutManager
 * @author cerridifebbo
 *
 */
public class MapLayout implements LayoutManager, Serializable {

	private static final long serialVersionUID = -5980749297800669540L;
	private int raw;
	private int column;
	private int preferredWidth, preferredHeight;
	private static final int BORDER = 5;

	/**
	 * This constructor sets the raw and the column of the grid.It also sets the
	 * sizes of the layout.
	 * 
	 * @see LayoutManager
	 * @author cerridifebbo
	 *
	 */
	public MapLayout(int raw, int column) {
		this.raw = raw;
		this.column = column;
		setSizes();
	}

	/**
	 * This method is empty because the layout has not need to make changes when
	 * this method is invoked.
	 * 
	 * @author cerridifebbo
	 * @param name
	 *            null
	 * @param comp
	 *            null
	 */
	@Override
	public void addLayoutComponent(String name, Component comp) {
		// No need to add layout components
	}

	/**
	 * This method is empty because the layout has not need to make changes when
	 * this method is invoked.
	 * 
	 * @author cerridifebbo
	 * @param comp
	 *            null
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		// No need to remove layout components
	}

	private void setSizes() {
		preferredWidth = (SectorButton.WIDTH - SectorButton.SHIFT_OBLIQUE_SIDE) * column + 4 * BORDER;
		preferredHeight = SectorButton.HEIGHT * raw + 4 * SectorButton.RADIUS + 4 * BORDER;
	}

	/**
	 * This method returns the preferred size of the layout. The preferred size
	 * are the size of the preferredWidth and preferredHeight plus the insets'
	 * size.
	 * 
	 * @author cerridifebbo
	 * @param parent
	 *            the container that uses the layout
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		setSizes();
		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;
		return dim;
	}

	/**
	 * This method returns the minimum size of the layout. The minimum size are
	 * the preferred size
	 * 
	 * @see preferredLayoutSize
	 * 
	 * @author cerridifebbo
	 * @param parent
	 *            the container that uses the layout
	 */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	/**
	 * This method sets the element of the parent parameter.
	 * 
	 * @author cerridifebbo
	 * @param parent
	 *            the container that uses the layout
	 */
	@Override
	public void layoutContainer(Container parent) {
		int x = BORDER;
		int y = BORDER;
		int currentRow = 0;
		int currentColumn = 0;
		setSizes();
		int nComponent = parent.getComponentCount();
		for (int i = 0; i < nComponent; i++, currentColumn++) {

			if (currentColumn % column == 0) {
				currentColumn = 0;
				currentRow++;
				x = BORDER;
				y = BORDER + SectorButton.RADIUS + currentRow * SectorButton.HEIGHT;
			}

			Component c = parent.getComponent(i);
			c.setBounds(x, y, SectorButton.WIDTH, SectorButton.HEIGHT);
			if (currentColumn % 2 == 0) {
				y = y + SectorButton.RADIUS + SectorButton.BORDER_HEIGHT;
			} else {
				y = y - SectorButton.RADIUS - SectorButton.BORDER_WIDTH;
			}
			x = x + SectorButton.WIDTH - SectorButton.SHIFT_OBLIQUE_SIDE;
		}
	}

}