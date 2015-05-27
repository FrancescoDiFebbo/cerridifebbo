package it.polimi.ingsw.cerridifebbo.view.cli;

import java.util.logging.Logger;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphic;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Player;

public class CLIGraphic extends Graphic {

	private static final Logger LOGGER = Logger.getLogger(CLIGraphic.class
			.getName());;

	@Override
	public void initialize(Map map, Player player) {

	}

	@Override
	public void sendMessage(String message) {
		LOGGER.info(message);
	}
}
