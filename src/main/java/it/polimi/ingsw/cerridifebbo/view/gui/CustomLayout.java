package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class CustomLayout implements LayoutManager {

	private static final int BORDER = 1;
	private int spaceY2;
	private int spaceY1;

	public CustomLayout(int spaceY1, int spaceY2) {
		this.spaceY1 = spaceY1;
		this.spaceY2 = spaceY2;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		// No need to add layout components
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		// No need to remove layout components
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension();
		Insets insets = parent.getInsets();
		dim.width = insets.left + insets.right + parent.getWidth();
		dim.height = insets.top + insets.bottom + parent.getHeight();
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
		int width;
		int height;
		Component temp;
		temp = parent.getComponent(0);
		width = temp.getWidth();
		height = temp.getHeight();
		temp.setBounds(x, y, width, height);
		y = y + spaceY1;
		x = x + width + spaceY2;
		for (int i = 1; i < parent.getComponentCount(); i++) {
			temp = parent.getComponent(i);
			width = temp.getWidth();
			height = temp.getHeight();
			temp.setBounds(x, y, temp.getWidth(), temp.getHeight());
			y = y + height + spaceY2;
		}

	}

}
