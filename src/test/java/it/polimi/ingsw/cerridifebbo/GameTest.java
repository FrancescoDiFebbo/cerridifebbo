package it.polimi.ingsw.cerridifebbo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cerridifebbo.controller.server.Server;
import it.polimi.ingsw.cerridifebbo.model.AlienPlayer;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.CharacterDeckFactory;
import it.polimi.ingsw.cerridifebbo.model.Deck;
import it.polimi.ingsw.cerridifebbo.model.EndGame;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.HumanPlayer;
import it.polimi.ingsw.cerridifebbo.model.ItemDeckFactory;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

public class GameTest {

	@Test
	public void test() {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		Game game = new Game(null, users);
		game.run();
		assertNotNull(game.getDeck());
		assertNotNull(game.getMap());
		assertNotNull(game.getUsers());
		game.checkGame();
		game.run();
		assertFalse(game.getState() instanceof EndGame);

		for (User u : users) {
			if (u.getPlayer() instanceof AlienPlayer) {
				AlienPlayer alien = (AlienPlayer) u.getPlayer();
				alien.kill();
			}
		}
		game.checkGame();
		game.run();
		assertTrue(game.getState() instanceof EndGame);

		for (User u : users) {
			if (u.getPlayer() instanceof HumanPlayer) {
				HumanPlayer human = (HumanPlayer) u.getPlayer();
				human.kill();
			}
		}
		assertTrue(game.getState() instanceof EndGame);
	}

	@Test
	public void testCards() {
		ArrayList<User> users = new ArrayList<User>();
		for (int i = 0; i < CharacterDeckFactory.MAX_PLAYERS; i++) {
			users.add(new User(UUID.randomUUID()));
		}
		Game game = new Game(new Server(), users);
		game.run();
		Deck deck = game.getDeck();
		Player player = null;
		for (User user : users) {
			if (user.getPlayer() instanceof HumanPlayer) {
				player = user.getPlayer();
				break;
			}
		}
		for (int i = 0; i < ItemDeckFactory.MAX_ITEM_CARDS; i++) {
			Card card = deck.drawItemCard();
			card.toString();
			player.addCard(deck.drawItemCard());
			card.performAction(player, game.getMap().getCell(5, 5), game);
		}
	}

	@Test
	public void testMovementAndAttack() {
		ArrayList<User> users = new ArrayList<User>();
		for (int i = 0; i < CharacterDeckFactory.MAX_PLAYERS; i++) {
			users.add(new User(UUID.randomUUID()));
		}
		Game game = new Game(new Server(), users);
		game.run();
		for (int i = 0; i < 39; i++) {
			for (int k = 0; k < CharacterDeckFactory.MAX_PLAYERS; k++) {
				Player player = game.getUsers().get(k).getPlayer();
				List<Sector> sectors = player.getPosition().getReachableSectors(player.getMaxMovement());
				int index = new Random().nextInt(sectors.size());
				player.movement(sectors.get(index), game);
				player.attack(game);
			}
		}
	}

	@Test
	public void testEscapeHatches() {
		ArrayList<User> users = new ArrayList<User>();
		for (int i = 0; i < CharacterDeckFactory.MAX_PLAYERS; i++) {
			users.add(new User(UUID.randomUUID()));
		}
		Game game = new Game(new Server(), users);
		game.run();
		List<Sector> hatches = game.getMap().getEscapeHatchSectors();
		int sector = 0;
		for (User user : game.getUsers()) {
			Player player = user.getPlayer();
			if (player instanceof HumanPlayer) {
				player.setPosition(hatches.get(sector));
				hatches.get(sector++).playerEnters(player, game.getDeck())
						.performAction(player, null, game);
			}
		}
	}
}
