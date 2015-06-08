package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Container;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ServerScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	private static final String INITIAL_MESSAGE = "For the last four years, medical équipe led by doctor Antonio Merz \n"
			+ "  has been working on spaceship SELVA to find the cure for \n"
			+ "  the Bellavita's disease, a brain-degenerative illness that has \n"
			+ "  been killing people on Earth by the millions.\n"
			+ "  In the last few weeks research had begun showing \n"
			+ "  positive results thorough the implantation of alien spores \n"
			+ "  in the cerebral cortex of a selected number of guinea pigs.\n"
			+ "  On the morning of the 26th of July chief medical attendant \n"
			+ "  Fabrizio Miraggio was put in quarantine following an incident \n"
			+ "  with one of the experimental animals: security personnel had to \n"
			+ "  forcefully block him while he was trying to devour Bibsy-332,\n"
			+ "  an experimental monkey carrying the spores.\n \n"
			+ "  On the 28th of July all communication with spaceship SELVA ceased.\n\n";

	private String playerRace;
	Container textContainer = new Container();

	public ServerScrollPane(String playerRace) {
		this.playerRace = playerRace;
		Border thickBorder = new LineBorder(
				GUIGraphics.getColorRace(playerRace), 2);
		setViewportView(textContainer);
		textContainer.setLayout(new BoxLayout(textContainer, BoxLayout.Y_AXIS));
		addText(INITIAL_MESSAGE);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		setSize(WIDTH, HEIGHT);
		verticalScrollBar.setBackground(GUIGraphics.BACKGROUND_COLOR);
		verticalScrollBar.setForeground(GUIGraphics.getColorRace(playerRace));
		verticalScrollBar.setAutoscrolls(true);
		setBorder(thickBorder);
	}

	public void addText(String message) {
		JTextArea text = new JTextArea("  " + message);
		text.setEditable(false);
		text.setFont(new Font("Arial", Font.BOLD, 11));
		text.setBackground(GUIGraphics.BACKGROUND_COLOR);
		text.setForeground(GUIGraphics.getColorRace(playerRace));
		textContainer.add(text);
		verticalScrollBar.setValue(textContainer.getHeight()+100000);
		verticalScrollBar.repaint();
	
	}
}
