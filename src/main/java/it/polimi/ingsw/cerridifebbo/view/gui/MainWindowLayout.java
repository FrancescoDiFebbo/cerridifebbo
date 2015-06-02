package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

public class MainWindowLayout implements LayoutManager {

	private static final int BORDER = 5;
	private static final int SPACE_BETWEEN_COMPONENTS = 20;
	private static final int SPACE_BEFORE_TIMER = 80;

	@Override
	public void addLayoutComponent(String name, Component comp) {
		//No need to add layout components
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		//No need to remove layout components
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension();
		Insets insets = parent.getInsets();
		dim.width = insets.left + insets.right + 1920;
		dim.height = insets.top + insets.bottom + 1080;
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
		y = y + SPACE_BEFORE_TIMER;
		x = x + width + SPACE_BETWEEN_COMPONENTS;
		for (int i = 1; i < parent.getComponentCount(); i++) {
			temp = parent.getComponent(i);
			width = temp.getWidth();
			height = temp.getHeight();
			temp.setBounds(x, y, temp.getWidth(), temp.getHeight());
			y = y + height + SPACE_BETWEEN_COMPONENTS;
		}

	}

}
