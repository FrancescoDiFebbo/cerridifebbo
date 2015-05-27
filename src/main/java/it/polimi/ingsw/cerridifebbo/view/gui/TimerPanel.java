package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class TimerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 50;
	private static final String TIME_FINISHED = "Time out!";
	private static final String INITIAL_MESSAGE = "00 00";
	private static final int delay = 90000;
	private static final int period = 1000;
	private int numberOfUpdate = 0;
	private JTextArea timeLeft;
	private Timer timer;
	private Timer updateTimer;

	// private ActionListener timeFinished = new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// timeLeft.setText(TIME_FINISHED);
	// timer.stop();
	//
	// }
	// };
	// private ActionListener refreshTimer = new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// numberOfUpdate++;
	// if (numberOfUpdate < delay / period) {
	// timeLeft.setText(String.valueOf((delay - period
	// * numberOfUpdate) / 1000));
	// } else {
	// updateTimer.stop();
	// }
	// }
	// };

	public TimerPanel() {

		timeLeft = new JTextArea(INITIAL_MESSAGE);
		timeLeft.setBackground(GUIGraphic.BACKGROUND_COLOR);
		timeLeft.setForeground(GUIGraphic.FOREGROUND_COLOR);
		timeLeft.setFont(new Font("Arial", Font.PLAIN, 20));
		timeLeft.setEditable(false);
		add(timeLeft);
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphic.BACKGROUND_COLOR);
		setForeground(GUIGraphic.FOREGROUND_COLOR);
		timeLeft.setVisible(false);

	}
}
