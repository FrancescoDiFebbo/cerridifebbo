package it.polimi.ingsw.cerridifebbo.view.gui;

/**
 * This class describes a no sector button.It extends the SectorButton class.
 * 
 * @see SectorButton
 * @author cerridifebbo
 *
 */
public class NoSectorButton extends SectorButton {

	private static final long serialVersionUID = 1L;

	/**
	 * This constructor calls the super constructor and sets the visibility to
	 * false.
	 * 
	 * @author cerridifebbo
	 * @param label
	 *            the name of the sector
	 */
	public NoSectorButton(String label) {
		super(label);
		this.setVisible(false);
	}
}