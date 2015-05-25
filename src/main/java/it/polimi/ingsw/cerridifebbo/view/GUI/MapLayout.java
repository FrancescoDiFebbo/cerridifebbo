package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.*;

public class MapLayout implements LayoutManager {
	private int raw;
	private int column;
	private int minWidth, minHeight;
	private int preferredWidth, preferredHeight;
	private static final int BORDER = 5;

	public MapLayout(int raw, int column) {
		this.raw = raw;
		this.column = column;
		setSizes();
	}

	/* Required by LayoutManager. */
	public void addLayoutComponent(String name, Component comp) {
	}

	/* Required by LayoutManager. */
	public void removeLayoutComponent(Component comp) {
	}

	private void setSizes() {
		preferredWidth = (SectorButton.WIDTH - SectorButton.SHIFT_OBLIQUE_SIDE)
				* column + 4 * BORDER;
		preferredHeight = SectorButton.HEIGHT * raw + 4 * SectorButton.RADIUS
				+ 4 * BORDER;
		minWidth = preferredWidth;
		minHeight = preferredHeight;
	}

	/* Required by LayoutManager. */
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		setSizes();
		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;
		return dim;
	}

	/* Required by LayoutManager. */
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

	/* Required by LayoutManager. */
	public void layoutContainer(Container parent) {
		int x = BORDER;
		int y = BORDER;
		int currentRaw = 0;
		int currentColumn = 0;
		setSizes();
		int nComponent = parent.getComponentCount();
		for (int i = 0; i < nComponent; i++, currentColumn++) {

			if (currentColumn % column == 0) {
				currentColumn = 0;
				currentRaw++;
				x = BORDER;
				y = BORDER + SectorButton.RADIUS + currentRaw
						* SectorButton.HEIGHT;
			}

			Component c = parent.getComponent(i);
			c.setBounds(x, y, SectorButton.WIDTH, SectorButton.HEIGHT);
			if (currentColumn % 2 == 0) {
				y = y + SectorButton.RADIUS;
			} else {
				y = y - SectorButton.RADIUS;
			}
			x = x + SectorButton.WIDTH - SectorButton.SHIFT_OBLIQUE_SIDE;
		}
	}

}