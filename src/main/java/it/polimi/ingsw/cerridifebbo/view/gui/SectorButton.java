package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public abstract class SectorButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final Color BORDER_COLOR = Color.BLACK;
	public static final Color HEXAGON_BORDER_COLOR = Color.WHITE;
	public static final Color PRESSED_BUTTON = Color.WHITE;
	public static final int SIDE = 24;
	public static final int SHIFT_OBLIQUE_SIDE = (int) ((int) SIDE * Math
			.sin((30 * Math.PI) / 180));
	public static final int RADIUS = (int) ((int) SIDE * Math
			.cos((30 * Math.PI) / 180));
	public static final int BORDER_WIDTH = 3;
	public static final int BORDER_HEIGHT = 3;
	public static final int WIDTH = SIDE + SHIFT_OBLIQUE_SIDE * 2 + 2
			* BORDER_WIDTH;
	public static final int HEIGHT = 2 * RADIUS + 2 * BORDER_HEIGHT;
	private static Polygon hexagon;
	private boolean pressed;

	public boolean isPressed() {
		return pressed;
	}

	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}

	public SectorButton(String label) {
		super(label);
		this.setName(label);
		initializeHexagon();
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setOpaque(false);
		pressed = false;

	}

	protected void initializeHexagon() {
		int[] cx, cy;
		cx = new int[] { 0, SHIFT_OBLIQUE_SIDE, SIDE + SHIFT_OBLIQUE_SIDE,
				SIDE + SHIFT_OBLIQUE_SIDE + SHIFT_OBLIQUE_SIDE,
				SIDE + SHIFT_OBLIQUE_SIDE, SHIFT_OBLIQUE_SIDE };
		cy = new int[] { RADIUS, RADIUS + RADIUS, RADIUS + RADIUS, RADIUS, 0, 0 };
		hexagon = new Polygon(cx, cy, 6);
	}

	@Override
	public boolean contains(int x, int y) {
		return hexagon.contains(x, y);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillPolygon(hexagon);
		g.setColor(BORDER_COLOR);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString(this.getName(), WIDTH / 2 - 10, HEIGHT / 2);
		g.setColor(HEXAGON_BORDER_COLOR);
		g.drawPolygon(hexagon);

	}

	@Override
	public void paintBorder(Graphics g) {
		// no button's borders
	}

}
