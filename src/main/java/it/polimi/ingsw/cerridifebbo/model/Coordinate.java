package it.polimi.ingsw.cerridifebbo.model;

import java.io.Serializable;

public class Coordinate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1215679744721260626L;
	private int column;
	private int row;

	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
}
