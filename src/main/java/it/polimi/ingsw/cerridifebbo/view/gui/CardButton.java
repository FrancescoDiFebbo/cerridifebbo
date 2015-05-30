package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class CardButton extends JButton {

	private static final long serialVersionUID = 1L;
	public static final String NO_CARD = "No card";

	public CardButton(String label) {
		setName(label);
		Border thickBorder = new LineBorder(GUIGraphics.FOREGROUND_COLOR, 2);
		setText(" " + label + " ");
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
		setBorder(thickBorder);

	}

}
