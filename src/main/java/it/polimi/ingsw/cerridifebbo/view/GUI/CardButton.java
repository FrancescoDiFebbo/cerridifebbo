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

public  class CardButton extends JButton implements ActionListener {


	public CardButton(String label) {
		super(label);
		addActionListener(this);
		this.setFont(new Font("Arial", Font.PLAIN, 12));
		this.setBackground(Color.BLACK);
		this.setForeground(Color.GREEN);
		this.setOpaque(false);
     	String ciao = System.getProperty("user.dir") + "\\map\\card.png";
		ImageIcon img = new ImageIcon(ciao);
		this.setIcon(img);

	}


	@Override
	public void actionPerformed(ActionEvent ev) {
		System.out.println("button clicked!" + this.getName());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		
		frame.setBackground(Color.BLACK);
		Container contentPane = frame.getContentPane();
		CardButton comp = new CardButton("Adrenaline Card");
		contentPane.setLayout(new GridLayout());
		contentPane.add(comp);
		CardButton comp2 = new CardButton("Sedatives Card");
		contentPane.add(comp2);
		CardButton comp3 = new CardButton("Sedatives Card");
		contentPane.add(comp3);
		CardButton comp4 = new CardButton("no card");
		contentPane.add(comp4);
		contentPane.setSize(200, 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

}
