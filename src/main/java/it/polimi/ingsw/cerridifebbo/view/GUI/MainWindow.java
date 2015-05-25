package it.polimi.ingsw.cerridifebbo.view.GUI;

import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.TextArea;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class MainWindow {

	private static final String FRAME_NAME = "ESCAPE FROM THE ALIENS IN OUTER SPACE";
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color FOREGROUND_COLOR = Color.GREEN;

	public static void main(String[] args) {

		JFrame frame = new JFrame(FRAME_NAME);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setBackground(BACKGROUND_COLOR);
		Container contentPane = frame.getContentPane();
		contentPane.setBackground(BACKGROUND_COLOR);
		contentPane.add(new MapContainer());
		JPanel timerButton = new TimerButton();
		contentPane.add(timerButton);
		ServerScrollPane server = new ServerScrollPane();
		contentPane.add(server);
		server.addText("nisdnf");
		contentPane.add(new CardsPanel());
		contentPane.add(new ButtonPanel());
		contentPane.setLayout(new MainWindowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

}
