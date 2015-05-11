package it.polimi.ingsw.cerridifebbo.model;

public class Sector {
	private Coordinate coordinate;
	private boolean passable;
	private Player[] playerInside;
	
	private Sector north;
	private Sector south;
	private Sector northEst;
	private Sector southEst;
	private Sector northWest;
	private Sector southWest;
	
	public Sector (int raw, int column, boolean passable){
		coordinate = new Coordinate (raw, column);
		setPassable(passable);
	}
	
	public void setAdjacentSector ()
	{
		
		setNorth();
		setSouth();
		setEstWest(coordinate.getColumn()%2);
		
		
	}

	private boolean checkOutOfBorders(int raw, int column)
	{
		if(raw < 0 || raw > Map.RAWMAP ||
		   column <0 || column > Map.COLUMNMAP)
			return false;
		return true;
	}
	
	private void setNorth ()
	{
		if(checkOutOfBorders(coordinate.getRaw()+1,coordinate.getColumn()))
		{
			//north
		}
		else
		{
			north = null;
		}
	}
	
	private void setSouth ()
	{
		if(checkOutOfBorders(coordinate.getRaw()-1,coordinate.getColumn()))
		{
			//south
		}
		else
		{
			south = null;
		}
	}
	
	private void setEstWest(int type)
	{
		int shiftColumnNorth=0;
		int shiftColumnSouth=0;
		
		switch (type)
		{
		case 0:
			shiftColumnNorth=-1;
		case 1:
			shiftColumnSouth=+1;
		}
		
		if(checkOutOfBorders(coordinate.getRaw()+shiftColumnNorth,coordinate.getColumn()+1))
		{
			//northEst
		}
		else
		{
			northEst = null;
		}
		if(checkOutOfBorders(coordinate.getRaw()+shiftColumnNorth,coordinate.getColumn()-1))
		{
			//northWest
		}
		else
		{
			northWest = null;
		}
		if(checkOutOfBorders(coordinate.getRaw()+shiftColumnSouth,coordinate.getColumn()+1))
		{
			//southEst
		}
		else
		{
			southEst = null;
		}
		if(checkOutOfBorders(coordinate.getRaw()+shiftColumnSouth,coordinate.getColumn()-1))
		{
			//southWest
		}
		else
		{
			southWest = null;
		}
	
	}
	
	public void playerEnters(Player player) {

	}

	public void playerLeaves(Player player) {

	}

	public boolean containsPlayer(Player player) {
		return false;
	}

	public boolean isPassable() {
		return passable;
	}

	public void setPassable(boolean passable) {
		this.passable = passable;
	}
}
