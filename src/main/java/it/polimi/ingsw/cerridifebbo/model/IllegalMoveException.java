package it.polimi.ingsw.cerridifebbo.model;

public class IllegalMoveException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String error;

	public IllegalMoveException(String error) {
		super();
		this.error = error;
	}

	public String getError() {
		return error;
	}

}
