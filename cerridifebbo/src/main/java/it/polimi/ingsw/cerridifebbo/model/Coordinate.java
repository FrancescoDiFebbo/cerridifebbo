package it.polimi.ingsw.cerridifebbo.model;

public class Coordinate {
	private int column;
	
	public int getColumn() {
		return column;
	}

	public int getRaw() {
		return raw;
	}

	private int raw;
	
	public Coordinate (int raw, int column)
	{
		this.raw=raw;
		this.column=column;
	}
}
