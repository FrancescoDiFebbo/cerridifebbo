package it.polimi.ingsw.cerridifebbo.model;

public class Coordinate {
	private int column;
	private int row;
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public Coordinate (int row, int column)
	{
		this.row=row;
		this.column=column;
	}
}
