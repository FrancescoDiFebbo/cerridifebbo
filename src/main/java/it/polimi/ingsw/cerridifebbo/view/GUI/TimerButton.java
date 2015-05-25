package it.polimi.ingsw.cerridifebbo.view.GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class TimerButton extends JPanel {

	public static final int WIDTH = 300;
	public static final int HEIGHT = 50;
	private static final String TIME_FINISHED = "Time out!";
	private static final int delay = 20000;
	private static final int period = 1000;
	private int numberOfUpdate = 0;
	private JTextArea timeLeft;

	private ActionListener timeFinished = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			timeLeft.setText(TIME_FINISHED);
			// TODO click end Turn Button
		}
	};
	private ActionListener refreshTimer = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			numberOfUpdate++;
			if (numberOfUpdate < delay / period) {
				timeLeft.setText(String.valueOf((delay - period
						* numberOfUpdate) / 1000));
			}
		}
	};

	public TimerButton() {
		Timer timer = new Timer(delay, timeFinished);
		Timer updateTimer = new Timer(period, refreshTimer);
		timer.start();
		updateTimer.start();
		timeLeft = new JTextArea(String.valueOf(delay / 1000));
		timeLeft.setBackground(MainWindow.BACKGROUND_COLOR);
		timeLeft.setForeground(MainWindow.FOREGROUND_COLOR);
		timeLeft.setFont(new Font("Arial", Font.PLAIN, 20));
		timeLeft.setEditable(false);
		add(timeLeft);
		setSize(WIDTH, HEIGHT);
		setBackground(MainWindow.BACKGROUND_COLOR);
		setForeground(MainWindow.FOREGROUND_COLOR);

	}
}
