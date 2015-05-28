package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CardButton extends JButton implements ActionListener {

	private static final long serialVersionUID = 1L;
	public static final String NO_CARD = "No card";

	public CardButton(String label) {
		super(label);
		setFont(new Font("Arial", Font.PLAIN, 10));
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setForeground(GUIGraphics.FOREGROUND_COLOR);
		setOpaque(false);
		if (label != NO_CARD) {
			String cardPath = System.getProperty("user.dir")
					+ "\\map\\card.png";
			ImageIcon img = new ImageIcon(cardPath);
			setIcon(img);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("button clicked! "+e.getActionCommand());
		
	}

}
