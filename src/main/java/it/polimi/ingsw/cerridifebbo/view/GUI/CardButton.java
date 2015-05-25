package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class CardButton extends JButton implements ActionListener {

	public static final String NO_CARD = "No card";
	private String cardName;

	public CardButton(String label) {
		super(label);
		cardName = label;
		addActionListener(this);
		this.setFont(new Font("Arial", Font.PLAIN, 12));
		this.setBackground(MainWindow.BACKGROUND_COLOR);
		this.setForeground(MainWindow.FOREGROUND_COLOR);
		this.setOpaque(false);
		if (label != NO_CARD) {
			String cardPath = System.getProperty("user.dir")
					+ "\\map\\card.png";
			ImageIcon img = new ImageIcon(cardPath);
			this.setIcon(img);
		}

	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		System.out.println("button clicked!" + cardName);
	}

}
