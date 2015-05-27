package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphic;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIGraphic extends Graphic {

	private static final String FRAME_NAME = "ESCAPE FROM THE ALIENS IN OUTER SPACE";
	public static final Color BACKGROUND_COLOR = Color.BLACK;
	public static final Color FOREGROUND_COLOR = Color.GREEN;
	private MapContainer mapGrid;
	private ServerScrollPane serverMessage;
	private JPanel timerButton;
	private CardsPanel cards;
	private ButtonPanel buttonPanel;
	private JFrame frame;
	private Container contentPane;

	@Override
	public void initialize(Map map, Player player) {

		frame = new JFrame(FRAME_NAME);
		contentPane = frame.getContentPane();
		contentPane.setBackground(BACKGROUND_COLOR);
		frame.setBackground(BACKGROUND_COLOR);
		mapGrid = new MapContainer(map);
		serverMessage = new ServerScrollPane();
		timerButton = new TimerPanel();
		cards = new CardsPanel(player);
		buttonPanel = new ButtonPanel();
		contentPane.add(mapGrid);
		contentPane.add(timerButton);
		contentPane.add(serverMessage);
		contentPane.add(cards);
		contentPane.add(buttonPanel);
		contentPane.setLayout(new MainWindowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	@Override
	public void sendMessage(String message) {
		serverMessage.addText(message);

	}

}
