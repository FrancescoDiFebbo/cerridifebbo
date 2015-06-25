package it.polimi.ingsw.cerridifebbo.controller.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The Interface RemoteUser serves to identify interfaces whose methods may be
 * invoked from a non-local virtual machine. The user on server can be called
 * through the methods provided by this interface.
 *
 * @author cerridifebbo
 */
public interface RemoteUser extends Remote {

	/**
	 * Send move.
	 *
	 * @param action
	 *            the action
	 * @param target
	 *            the target
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void sendMove(String action, String target) throws RemoteException;

}
