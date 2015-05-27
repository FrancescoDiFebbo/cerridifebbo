package it.polimi.ingsw.cerridifebbo;

import static org.junit.Assert.*;
import it.polimi.ingsw.cerridifebbo.model.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import org.junit.Test;

public class CheckGameTest {
	
	@Test
	public void test() {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		Game game = new Game(null, users);
		game.run();
		game.checkGame();
		game.run();
		assertFalse(game.getState() instanceof EndGame);
		
		for (User u : users){
			if(u.getPlayer() instanceof AlienPlayer){
				AlienPlayer alien = (AlienPlayer) u.getPlayer();
				alien.kill();
			}	
		}
		game.checkGame();
		game.run();
		assertTrue(game.getState() instanceof EndGame);
		
		for (User u : users){
			if(u.getPlayer() instanceof HumanPlayer){
				HumanPlayer human = (HumanPlayer) u.getPlayer();
				human.kill();
			}	
		}
		assertTrue(game.getState() instanceof EndGame);
		
			
	}

}
