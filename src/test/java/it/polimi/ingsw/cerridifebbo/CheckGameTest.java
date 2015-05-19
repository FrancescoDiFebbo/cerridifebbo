package it.polimi.ingsw.cerridifebbo;

import static org.junit.Assert.*;
import it.polimi.ingsw.cerridifebbo.model.*;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

public class CheckGameTest {
	
	@Test
	public void test() {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		Game game = new Game(users);
		game.run();
		game.setState(new CheckGame(game));
		game.run();
		assertFalse(game.getState() instanceof EndGame);
		
		for (User u : users){
			if(u.getPlayer() instanceof AlienPlayer){
				AlienPlayer alien = (AlienPlayer) u.getPlayer();
				alien.kill();
			}	
		}
		game.setState(new CheckGame(game));
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
