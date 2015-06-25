package it.polimi.ingsw.cerridifebbo;

import static org.junit.Assert.*;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Sector;

import org.junit.Test;

public class MapTest {

	@Test
	public void testReachableSectors() {
		Map map = Map.getInstance();
		Sector start = map.getCell(5, 5);
		Sector end = map.getCell(7, 9);
		int maxMovement = 4;
		assertTrue(start.getReachableSectors(maxMovement).contains(end));
		start = map.getCell(5, 5);
		end = map.getCell(10, 11);
		assertFalse(start.getReachableSectors(maxMovement).contains(end));
	}

	@Test
	public void testMapRemote() {
		Map map = Map.getInstance();
		@SuppressWarnings("unused")
		MapRemote remote = map.getMapRemote();
	}
}
