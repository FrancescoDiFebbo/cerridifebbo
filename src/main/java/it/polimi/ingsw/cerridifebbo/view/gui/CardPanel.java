package it.polimi.ingsw.cerridifebbo.view.gui;

import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * This class describes a panel card. The panel is formed by an image of a card,
 * a name of the card and two buttons use and discard.
 * 
 * @see CustomLayout that is the layout of this class.
 * @author cerridifebbo
 *
 */
public class CardPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton use;
	private JButton discard;
	private JLabel card;
	public static final String USE_TEXT = "use";
	public static final String DISCARD_TEXT = "discard";
	public static final int PANEL_WIDTH = 130;
	public static final int PANEL_HEIGHT = 80;
	public static final int CARD_WIDTH = 60;
	public static final int CARD_HEIGHT = 70;
	public static final int BUTTON_WIDTH = 53;
	public static final int BUTTON_HEIGHT = 30;
	private static final int SPACE_BETWEEN_COMP_Y2 = 2;
	private static final int SPACE_BETWEEN_COMP_Y1 = 0;
	private static final String FONT_NAME = "Arial";

	/**
	 * This constructor creates the panel and the buttons.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the card
	 * @param playerRace
	 *            the race of the player
	 *
	 */
	public CardPanel(String label, String playerRace) {
		LayoutManager cardLayout = new CustomLayout(SPACE_BETWEEN_COMP_Y1,
				SPACE_BETWEEN_COMP_Y2);
		setLayout(cardLayout);
		setSize(PANEL_WIDTH, PANEL_HEIGHT);
		setMinimumSize(cardLayout.minimumLayoutSize(this));
		card = new JLabel();
		setBackground(GUIGraphics.BACKGROUND_COLOR);
		setOpaque(true);
		setName(label);
		initializeCardLabel(label, playerRace);
		initializeButton(playerRace);
	}

	/**
	 * This constructor initializes the card's label.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the card
	 * @param playerRace
	 *            the race of the player
	 *
	 */
	private void initializeCardLabel(String label, String playerRace) {
		card = new JLabel();
		card.setName(label);
		card.setVerticalTextPosition(AbstractButton.BOTTOM);
		card.setHorizontalTextPosition(AbstractButton.CENTER);
		card.setText(" " + label + " ");
		card.setFont(new Font(FONT_NAME, Font.PLAIN, 10));
		card.setBackground(GUIGraphics.BACKGROUND_COLOR);
		card.setForeground(GUIGraphics.getColorRace(playerRace));
		card.setOpaque(false);
		String cardPath = System.getProperty("user.dir")
				+ System.getProperty("file.separator") + "file"
				+ System.getProperty("file.separator") + "card.png";
		ImageIcon img = new ImageIcon(cardPath);
		card.setIcon(img);
		card.setSize(CARD_WIDTH, CARD_HEIGHT);
		add(card);
	}

	/**
	 * This method initialize the use and discard buttons.
	 * 
	 * @author cerridifebbo
	 * @param playerRace
	 *            the race of the player
	 */
	private void initializeButton(String playerRace) {
		UIManager.getDefaults().put("Button.disabledText",
				GUIGraphics.getColorRace(playerRace));
		use = new JButton(USE_TEXT);
		use.setFont(new Font(FONT_NAME, Font.PLAIN, 10));
		use.setName(card.getName());
		use.setBackground(GUIGraphics.BACKGROUND_COLOR);
		use.setForeground(GUIGraphics.getColorRace(playerRace));
		use.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		discard = new JButton(DISCARD_TEXT);
		discard.setName(card.getName());
		discard.setBackground(GUIGraphics.BACKGROUND_COLOR);
		discard.setForeground(GUIGraphics.getColorRace(playerRace));
		discard.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
		discard.setFont(new Font(FONT_NAME, Font.PLAIN, 10));
		add(use);
		add(discard);
		discard.setEnabled(false);
		use.setEnabled(false);
		Border thickBorder = new LineBorder(
				GUIGraphics.getColorRace(playerRace), 1);
		discard.setBorder(thickBorder);
		use.setBorder(thickBorder);
	}

	/**
	 * getter of use
	 */
	public JButton getUse() {
		return use;
	}

	/**
	 * getter of discard
	 */
	public JButton getDiscard() {
		return discard;
	}

}
