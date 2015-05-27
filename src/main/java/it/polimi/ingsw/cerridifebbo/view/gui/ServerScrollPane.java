package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	private static final String INITIAL_MESSAGE = "Welcome!";
	Container textContainer = new Container();

	public ServerScrollPane() {

		setViewportView(textContainer);
		textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
		addText(INITIAL_MESSAGE);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setSize(WIDTH, HEIGHT);
	}

	public void addText(String message) {
		JTextArea text = new JTextArea(message);
		text.setEditable(false);
		text.setBackground(GUIGraphic.BACKGROUND_COLOR);
		text.setForeground(GUIGraphic.FOREGROUND_COLOR);
		textContainer.add(text);
	}
}
