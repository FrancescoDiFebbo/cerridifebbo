package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.model.Game;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class TimerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 300;
	public static final int HEIGHT = 50;
	private static final String TIME_FINISHED = "Time out!";
	private static final int DELAY = Game.MAX_TIMEOUT;
	private static final int PERIOD = 1000;
	private int numberOfUpdate;
	private JTextArea timeLeft;
	private Timer timer;

	private transient ActionListener updateTimer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			numberOfUpdate++;
			if (numberOfUpdate < DELAY / PERIOD) {
				int remainingSeconds = (DELAY - PERIOD * numberOfUpdate) / 1000;
				int remainingMinutes = 0;
				while (remainingSeconds >= 60) {
					remainingSeconds = remainingSeconds - 60;
					remainingMinutes++;
				}
				timeLeft.setText(String.valueOf(remainingMinutes + " : "
						+ remainingSeconds));
			} else {
				timeLeft.setText(TIME_FINISHED);
				timer.stop();
			}
		}
	};

	public TimerPanel(String playerRace) {

		timeLeft = new JTextArea();
		timeLeft.setBackground(GUIGraphics.BACKGROUND_COLOR);
		timeLeft.setForeground(GUIGraphics.getColorRace(playerRace));
		timeLeft.setFont(new Font("Arial", Font.PLAIN, 40));
		timeLeft.setEditable(false);
		add(timeLeft);
		setSize(WIDTH, HEIGHT);
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setForeground(GUIGraphics.getColorRace(playerRace));
	}

	public void startTimer() {
		numberOfUpdate = 0;
		timer = new Timer(PERIOD, updateTimer);
		timer.start();
	}

	public void stopTimer() {
		timer.stop();
		timeLeft.setText("");
	}

}
