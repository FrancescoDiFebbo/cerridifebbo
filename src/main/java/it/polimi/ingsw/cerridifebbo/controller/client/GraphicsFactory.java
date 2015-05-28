package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.view.cli.CLIGraphics;
import it.polimi.ingsw.cerridifebbo.view.gui.GUIGraphics;

public class GraphicsFactory {
	public static final String GUI_INTERFACE = "gui_interface";
	public static final String CLI_INTERFACE = "cli_interface";
	
	

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
