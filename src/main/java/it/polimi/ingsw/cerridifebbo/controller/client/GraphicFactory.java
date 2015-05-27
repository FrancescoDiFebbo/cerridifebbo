package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.view.cli.CLIGraphic;
import it.polimi.ingsw.cerridifebbo.view.gui.GUIGraphic;

public class GraphicFactory {
	public static final String GUI_INTERFACE = "gui_interface";
	public static final String CLI_INTERFACE = "cli_interface";

	public static Graphic getInterface(String param) {
		switch (param) {
		case GUI_INTERFACE:
			return new GUIGraphic();
		case CLI_INTERFACE:
			return new CLIGraphic();
		default:
			return null;
		}
	}
}
