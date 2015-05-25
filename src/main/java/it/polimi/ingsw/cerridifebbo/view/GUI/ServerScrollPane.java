package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ServerScrollPane extends JScrollPane {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	Container textContainer = new Container();

	public ServerScrollPane() {

		setViewportView(textContainer);
		textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setSize(WIDTH, HEIGHT);
	}

	public void addText(String message) {
		JTextArea text = new JTextArea(message);
		text.setEditable(false);
		text.setBackground(MainWindow.BACKGROUND_COLOR);
		text.setForeground(MainWindow.FOREGROUND_COLOR);
		textContainer.add(text);
	}
}
