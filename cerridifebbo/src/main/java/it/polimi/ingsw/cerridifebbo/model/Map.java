package it.polimi.ingsw.cerridifebbo.model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Map {
	
	private String name;
	private final int COLUMNMAP = 23;
	private final int RAWMAP = 14;
	
	
	private Sector[][] grid = new Sector [RAWMAP][COLUMNMAP];
	
	
	public void createMap(File mapFile) {
		
		try{
			SectorFactory factory = new ConcreteSectorFactory();
			Scanner fr = new Scanner(mapFile);
			name= mapFile.getName();
			for(int i=0; i<RAWMAP; i++ )
			{
				for (int j=0; j<COLUMNMAP; j++ )
				{
					grid[i][j] = factory.createSector(fr.nextInt(),i ,j);
				}
			}
			fr.close();
		}
		catch (IOException e)
		{
		    e.printStackTrace();
		}
	}
	
	public  static void main(String[] args)
	{
		File a = new File ("C://Users//stefano//git//cerridifebbo//cerridifebbo//map//galilei.txt");
		Map ao = new Map();
		ao.createMap(a);
	}
	
	
	
}
