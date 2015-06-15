package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.view.cli.CLIGraphics;
import it.polimi.ingsw.cerridifebbo.view.gui.GUIGraphics;

/**
 * This class is the concrete class for the factory design patter. It creates
 * the type of graphics that the client choose.
 * 
 * @author cerridifebbo
 * 
 *
 */
public class GraphicsFactory {
	public static final String GUI_INTERFACE = "gui_interface";
	public static final String CLI_INTERFACE = "cli_interface";

	/**
	 * Private constructor of the class.
	 * 
	 * @author cerridifebbo
	 */
	private GraphicsFactory() {

	}

	/**
	 * This method creates the type of graphics that is indicated by the
	 * parameter param
	 * 
	 * @author cerridifebbo
	 * @param param
	 *            the type of the graphics
	 * @return a new graphics. Could be GUI Interface or CLI Interface.If param
	 *         is unknown the return value is null
	 */
	public static Graphics getInterface(String param) {
		switch (param) {
		case GUI_INTERFACE:
			return new GUIGraphics();
		case CLI_INTERFACE:
			return new CLIGraphics();
		default:
			return null;
		}
	}
}
