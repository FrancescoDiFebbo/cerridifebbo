package it.polimi.ingsw.cerridifebbo;

import static org.junit.Assert.*;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.util.ArrayList;

import org.junit.Test;

public class GameTest {

	@Test
	public void test() {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User());
		users.add(new User());
		users.add(new User());
		Game game = new Game(users);
		game.run();
		assertNotNull(game.getDeck());
		assertNotNull(game.getMap());
		assertNotNull(game.getUsers());
	}
}
