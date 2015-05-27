package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Color;
import java.awt.Dimension;
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
	public static final Color PRESSED_BUTTON = Color.WHITE;
	public static final int SIDE = 24;
	public static final int SHIFT_OBLIQUE_SIDE = (int) ((int) SIDE * Math
			.sin((30 * Math.PI) / 180));
	public static final int RADIUS = (int) ((int) SIDE * Math
			.cos((30 * Math.PI) / 180));
	public static final int BORDER_WIDTH = 2;
	public static final int BORDER_HEIGHT = 2;
	public static final int WIDTH = SIDE + SHIFT_OBLIQUE_SIDE * 2 + 2
			* BORDER_WIDTH;
	public static final int HEIGHT = 2 * RADIUS + 2 * BORDER_HEIGHT;
	private static Polygon shape;
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
		addActionListener(this);
		setBackground(GUIGraphic.BACKGROUND_COLOR);
		setOpaque(false);
		pressed = false;

	}

	protected void initializeHexagon() {
		int[] cx, cy;
		cx = new int[] { 0, SHIFT_OBLIQUE_SIDE, SIDE + SHIFT_OBLIQUE_SIDE,
				SIDE + SHIFT_OBLIQUE_SIDE + SHIFT_OBLIQUE_SIDE,
				SIDE + SHIFT_OBLIQUE_SIDE, SHIFT_OBLIQUE_SIDE };
		cy = new int[] { RADIUS, RADIUS + RADIUS, RADIUS + RADIUS, RADIUS, 0, 0 };
		shape = new Polygon(cx, cy, 6);
		this.setPreferredSize(new Dimension(2 * SHIFT_OBLIQUE_SIDE + SIDE,
				2 * RADIUS));

	}

	@Override
	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.fillPolygon(shape);
		g.setColor(BORDER_COLOR);
		g.drawPolygon(shape);
		g.setFont(new Font("Arial", Font.BOLD, 11));
		g.drawString(this.getName(), WIDTH / 2 - 10, HEIGHT / 2);

	}

	@Override
	public void paintBorder(Graphics g) {

	}

}