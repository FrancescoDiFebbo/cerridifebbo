package it.polimi.ingsw.cerridifebbo.controller.common;

import java.rmi.Remote;

/**
 * The Interface RemoteClient serves to identify interfaces whose methods may be
 * invoked from a non-local virtual machine. The client can be called through
 * the methods provided by this interface.
 *
 * @author cerridifebbo
 */
public interface RemoteClient extends Remote, ClientConnection {

}
