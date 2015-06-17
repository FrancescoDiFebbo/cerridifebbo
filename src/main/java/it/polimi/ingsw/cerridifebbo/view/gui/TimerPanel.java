package it.polimi.ingsw.cerridifebbo.view.gui;

import it.polimi.ingsw.cerridifebbo.model.Game;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;

/**
 * This class describes a TimerPanel that is a JPanel with a displayed timer.
 * 
 * @author cerridifebbo
 *
 */
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

	private transient ActionListener updateTimer = new TimerActionListener();

	/**
	 * This constructor creates the JPanel and the JtextArea.
	 * 
	 * @author cerridifebbo
	 * @param playerRace
	 *            the race of the player
	 */
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

	/**
	 * This method starts the timer.
	 * 
	 * @author cerridifebbo
	 */
	public void startTimer() {
		numberOfUpdate = 0;
		timer = new Timer(PERIOD, updateTimer);
		timer.start();
	}

	/**
	 * This method stops the timer.
	 * 
	 * @author cerridifebbo
	 */
	public void stopTimer() {
		timer.stop();
		timeLeft.setText("");
	}

	/**
	 * This class implements ActionListener.
	 * 
	 * @author cerridifebbo
	 *
	 */
	private class TimerActionListener implements ActionListener {

		/**
		 * This method displays the time left in the format Minutes:Seconds.
		 * 
		 * @author cerridifebbo
		 * @param e
		 *            the actionEvent that invokes the method
		 */
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
	}
}
