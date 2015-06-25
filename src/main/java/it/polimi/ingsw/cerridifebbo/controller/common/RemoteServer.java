package it.polimi.ingsw.cerridifebbo.controller.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface RemoteServer serves to identify interfaces whose methods may be
 * invoked from a non-local virtual machine. The server can be called through
 * the methods provided by this interface.
 *
 * @author cerridifebbo
 */
public interface RemoteServer extends Remote {

	/**
	 * Register on server.
	 *
	 * @param username
	 *            the username
	 * @param address
	 *            the address
	 * @param port
	 *            the port
	 * @return true, if successful
	 * @throws RemoteException
	 *             the remote exception
	 */
	public boolean registerOnServer(String username, String address, int port) throws RemoteException;
}
