package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * This class is a custom layout. It puts the first element of the container in
 * the top-left part of the container and then all the other components in the
 * right part of the container one under the other.
 * 
 * @see LayoutManager
 * @author cerridifebbo
 *
 */
public class CustomLayout implements LayoutManager {

	private static final int BORDER = 1;
	private int spaceY2;
	private int spaceY1;

	/**
	 * This constructor set the spaceY1 and the spaceY2
	 * 
	 * @author cerridifebbo
	 * @param spaceY1
	 *            the y-axis space before the second element.
	 * @param spaceY2
	 *            the y-axis space between each element
	 */
	public CustomLayout(int spaceY1, int spaceY2) {
		this.spaceY1 = spaceY1;
		this.spaceY2 = spaceY2;
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

	/**
	 * This method returns the preferred size of the layout. The preferred size
	 * are the size of the container plus the insets' size.
	 * 
	 * @author cerridifebbo
	 * @param parent
	 *            the container that uses the layout
	 */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Dimension dim = new Dimension();
		Insets insets = parent.getInsets();
		dim.width = insets.left + insets.right + parent.getWidth();
		dim.height = insets.top + insets.bottom + parent.getHeight();
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
