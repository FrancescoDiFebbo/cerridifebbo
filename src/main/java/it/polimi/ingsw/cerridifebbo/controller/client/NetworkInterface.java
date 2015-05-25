package it.polimi.ingsw.cerridifebbo.controller.client;

import java.io.IOException;

import it.polimi.ingsw.cerridifebbo.controller.common.RemoteServer;

public interface NetworkInterface extends RemoteServer {

	public void connect() throws IOException;

	public void close() throws IOException;
}
