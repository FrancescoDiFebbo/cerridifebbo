package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.*;

public class MapLayout implements LayoutManager {
	private int raw;
	private int column;
	private int preferredWidth, preferredHeight;
	private static final int BORDER = 5;

	public MapLayout(int raw, int column) {
		this.raw = raw;
		this.column = column;
		setSizes();
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

	private void setSizes() {
		preferredWidth = (SectorButton.WIDTH - SectorButton.SHIFT_OBLIQUE_SIDE)
				* column + 4 * BORDER;
		preferredHeight = SectorButton.HEIGHT * raw + 4 * SectorButton.RADIUS
				+ 4 * BORDER;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension(0, 0);
		setSizes();
		Insets insets = parent.getInsets();
		dim.width = preferredWidth + insets.left + insets.right;
		dim.height = preferredHeight + insets.top + insets.bottom;
		return dim;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return preferredLayoutSize(parent);
	}

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
				y = BORDER + SectorButton.RADIUS + currentRow
						* SectorButton.HEIGHT;
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