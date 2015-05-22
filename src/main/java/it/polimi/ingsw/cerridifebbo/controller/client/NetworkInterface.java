package it.polimi.ingsw.cerridifebbo.controller.client;

import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;

public interface NetworkInterface extends RemoteServer {

	boolean connect();

	boolean close();
}
