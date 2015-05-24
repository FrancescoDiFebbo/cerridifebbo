package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public abstract class SectorButton extends JButton implements ActionListener {

	public static final int SIDE = 24;
	public static final int SHIFT_OBLIQUE_SIDE = (int) ((int) SIDE * Math
			.sin((30 * Math.PI) / 180));
	public static final int RADIUS = (int) ((int) SIDE * Math
			.cos((30 * Math.PI) / 180));
	public static final int BORDER_WIDTH = SIDE / 6;
	public static final int BORDER_HEIGHT = RADIUS / 6;
	public static final int WIDTH = SIDE + SHIFT_OBLIQUE_SIDE * 2
			+ BORDER_WIDTH;
	public static final int HEIGHT = 2 * RADIUS + BORDER_HEIGHT;
	private static Polygon shape;

	public SectorButton(String label) {
		super(label);
		this.setName(label);
		initializeHexagon();
		addActionListener(this);
		this.setFont(new Font("Arial", Font.PLAIN, 8));
		this.setBackground(Color.BLACK);
		this.setOpaque(false);

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
	public void actionPerformed(ActionEvent ev) {
		System.out.println("button clicked!" + this.getName());
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g);
		g.drawPolygon(shape);

	}

	@Override
	public void paintBorder(Graphics g) {

	}

}
