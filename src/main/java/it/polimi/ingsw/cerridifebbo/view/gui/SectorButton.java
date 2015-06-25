package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import javax.swing.JButton;

/**
 * This class describes a generic sector button. It extends the JButton class.
 * 
 * @see Jbutton
 * @author cerridifebbo
 *
 */
public abstract class SectorButton extends JButton {

	private static final long serialVersionUID = 1L;
	public static final Color FONT_COLOR = Color.BLACK;
	public static final Color HEXAGON_BORDER_COLOR = Color.WHITE;
	public static final Color PRESSED_BUTTON = Color.WHITE;
	public static final int SIDE = 24;
	public static final int SHIFT_OBLIQUE_SIDE = (int) (SIDE * Math.sin((30 * Math.PI) / 180));
	public static final int RADIUS = (int) (SIDE * Math.cos((30 * Math.PI) / 180));
	public static final int BORDER_WIDTH = 3;
	public static final int BORDER_HEIGHT = 3;
	public static final int WIDTH = SIDE + SHIFT_OBLIQUE_SIDE * 2 + 2 * BORDER_WIDTH;
	public static final int HEIGHT = 2 * RADIUS + 2 * BORDER_HEIGHT;
	private static Polygon hexagon;

	/**
	 * This constructor creates the sectorButton it also set the name of the
	 * sector.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the sector
	 */
	public SectorButton(String label) {
		super(label);
		this.setName(label);
		initializeHexagon();
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setOpaque(false);
	}

	/**
	 * This methods initializes the hexagon Polygon
	 * 
	 * @author cerridifebbo
	 */
	private void initializeHexagon() {
		int[] cx, cy;
		cx = new int[] { 0, SHIFT_OBLIQUE_SIDE, SIDE + SHIFT_OBLIQUE_SIDE, SIDE + SHIFT_OBLIQUE_SIDE + SHIFT_OBLIQUE_SIDE, SIDE + SHIFT_OBLIQUE_SIDE,
				SHIFT_OBLIQUE_SIDE };
		cy = new int[] { RADIUS, RADIUS + RADIUS, RADIUS + RADIUS, RADIUS, 0, 0 };
		hexagon = new Polygon(cx, cy, 6);
	}

	/**
	 * This methods override the precedent contains. It return if the coordinate
	 * x and y are inside the hexagon polygon.
	 * 
	 * @author cerridifebbo
	 * @param x
	 *            the coordinate x
	 * @param y
	 *            the coordinate y
	 * @return true if is contained , false otherwise
	 */

	@Override
	public boolean contains(int x, int y) {
		return hexagon.contains(x, y);
	}

	/**
	 * This methods override the precedent paintComponent. It paints the hexagon
	 * in the button and paints also the label name.
	 * 
	 * @author cerridifebbo
	 * @param g
	 *            the graphics
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillPolygon(hexagon);
		g.setColor(FONT_COLOR);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString(this.getName(), WIDTH / 2 - 10, HEIGHT / 2);
		g.setColor(HEXAGON_BORDER_COLOR);
		g.drawPolygon(hexagon);

	}

	/**
	 * This methods is empty because there is no need to paint the border of the
	 * sector.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void paintBorder(Graphics g) {
		// no button's borders
	}

}
