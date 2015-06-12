package it.polimi.ingsw.cerridifebbo.model;

import java.io.Serializable;

/**
 * This class describes a generic coordinate
 * 
 * @author cerridifebbo
 */

public class Coordinate implements Serializable {

	private static final long serialVersionUID = -1215679744721260626L;
	private int column;
	private int row;

	/**
	 * Constructor method. Set row and column.
	 * 
	 * @author cerridifebbo
	 *
	 */
	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * getter of column
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * getter of rowF
	 */
	public int getRow() {
		return row;
	}
}
