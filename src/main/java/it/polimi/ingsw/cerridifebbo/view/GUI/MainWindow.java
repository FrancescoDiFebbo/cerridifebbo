package it.polimi.ingsw.cerridifebbo.view.GUI;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class MainWindow {

	private static final String FRAME_NAME = "ESCAPE FROM THE ALIENS IN OUTER SPACE";

	public static void main(String[] args) {

		JFrame frame = new JFrame(FRAME_NAME);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBackground(Color.BLACK);
		Container contentPane = frame.getContentPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.setLayout(new GridLayout());
		contentPane.add(new MapContainer());
		// Panel cards = new Panel();
		// cards.add(new CardButton("Sedatives"));
		// cards.add(new CardButton("adrenaline"));
		// cards.add(new CardButton("Sedatives"));
		// contentPane.add(cards);
		// contentPane.add(new JButton(), new Timer(1000,null));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

}
