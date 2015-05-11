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
	
	private void setEstWest(int type)
	{
		int column = coordinate.getColumn();
		int raw =coordinate.getRaw();
		switch (type)
		{
		case 0:
			if(checkOutOfBorders(coordinate.getRaw()-1,coordinate.getColumn()+1))
			{
				//northEst
			}
			else
			{
				northEst = null;
			}
			if(checkOutOfBorders(coordinate.getRaw()-1,coordinate.getColumn()-1))
			{
				//northWest
			}
			else
			{
				northWest = null;
			}
			if(checkOutOfBorders(coordinate.getRaw(),coordinate.getColumn()+1))
			{
				//southEst
			}
			else
			{
				southEst = null;
			}
			if(checkOutOfBorders(coordinate.getRaw(),coordinate.getColumn()-1))
			{
				//southWest
			}
			else
			{
				southWest = null;
			}
			break;
			
		case 1:
			
			if(checkOutOfBorders(coordinate.getRaw(),coordinate.getColumn()+1))
			{
				//northEst
			}
			else
			{
				northEst = null;
			}
			if(checkOutOfBorders(coordinate.getRaw(),coordinate.getColumn()-1))
			{
				//northWest
			}
			else
			{
				northWest = null;
			}
			if(checkOutOfBorders(coordinate.getRaw()+1,coordinate.getColumn()+1))
			{
				//southEst
			}
			else
			{
				southEst = null;
			}
			if(checkOutOfBorders(coordinate.getRaw()+1,coordinate.getColumn()-1))
			{
				//southWest
			}
			else
			{
				southWest = null;
			}
		}
	
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
