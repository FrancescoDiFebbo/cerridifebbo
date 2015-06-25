package it.polimi.ingsw.cerridifebbo;

import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cerridifebbo.controller.server.User;
import it.polimi.ingsw.cerridifebbo.model.AdrenalineItemCard;
import it.polimi.ingsw.cerridifebbo.model.AttackItemCard;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.Deck;
import it.polimi.ingsw.cerridifebbo.model.DefenseItemCard;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.HumanPlayer;
import it.polimi.ingsw.cerridifebbo.model.HumanSector;
import it.polimi.ingsw.cerridifebbo.model.ItemDeckFactory;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.SedativesItemCard;
import it.polimi.ingsw.cerridifebbo.model.SpotlightItemCard;
import it.polimi.ingsw.cerridifebbo.model.TeleportItemCard;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.junit.Test;

public class ItemCardsTest {
	@Test
	public void testAdrenaline() {
		ArrayList<User> users = new ArrayList<User>();

		try {
			users.add(new User(String.valueOf(1), null));
			users.add(new User(String.valueOf(2), null));
		} catch (RemoteException e) {
		}

		Game game = new Game(users);
		game.run();
		Deck deck = game.getDeck();
		deck.reset();
		Player player = null;
		for (User user : users) {
			if (user.getPlayer() instanceof HumanPlayer) {
				player = user.getPlayer();
				boolean found = false;
				Card card = null;
				for (int i = 0; !found && i < ItemDeckFactory.MAX_ITEM_CARDS; i++) {
					card = deck.drawItemCard();
					if (card instanceof AdrenalineItemCard) {
						found = true;
					}
				}
				card.performAction(player, null, game);
				assertTrue(user.getPlayer().getMaxMovement() == AdrenalineItemCard.ADRENALINEMOVEMENT);
			}

		}
	}

	@Test
	public void testSpotlight() {
		ArrayList<User> users = new ArrayList<User>();

		try {
			users.add(new User(String.valueOf(1), null));
			users.add(new User(String.valueOf(2), null));
		} catch (RemoteException e) {
		}

		Game game = new Game(users);
		game.run();
		Deck deck = game.getDeck();
		deck.reset();
		Player player = null;
		for (User user : users) {
			if (user.getPlayer() instanceof HumanPlayer) {
				player = user.getPlayer();
				boolean found = false;
				Card card = null;
				for (int i = 0; !found && i < ItemDeckFactory.MAX_ITEM_CARDS; i++) {
					card = deck.drawItemCard();
					if (card instanceof SpotlightItemCard) {
						found = true;
					}
				}
				card.performAction(player, game.getMap().getCell(5, 5), game);

			}

		}
	}

	@Test
	public void testSedatives() {
		ArrayList<User> users = new ArrayList<User>();

		try {
			users.add(new User(String.valueOf(1), null));
			users.add(new User(String.valueOf(2), null));
		} catch (RemoteException e) {
		}

		Game game = new Game(users);
		game.run();
		Deck deck = game.getDeck();
		deck.reset();
		Player player = null;
		for (User user : users) {
			if (user.getPlayer() instanceof HumanPlayer) {
				player = user.getPlayer();
				boolean found = false;
				Card card = null;
				for (int i = 0; !found && i < ItemDeckFactory.MAX_ITEM_CARDS; i++) {
					card = deck.drawItemCard();
					if (card instanceof SedativesItemCard) {
						found = true;
					}
				}
				card.performAction(player, null, game);

			}

		}
	}

	@Test
	public void testDefense() {
		ArrayList<User> users = new ArrayList<User>();

		try {
			users.add(new User(String.valueOf(1), null));
			users.add(new User(String.valueOf(2), null));
		} catch (RemoteException e) {
		}

		Game game = new Game(users);
		game.run();
		Deck deck = game.getDeck();
		deck.reset();
		Player player = null;
		for (User user : users) {
			if (user.getPlayer() instanceof HumanPlayer) {
				player = user.getPlayer();
				boolean found = false;
				Card card = null;
				for (int i = 0; !found && i < ItemDeckFactory.MAX_ITEM_CARDS; i++) {
					card = deck.drawItemCard();
					if (card instanceof DefenseItemCard) {
						found = true;
					}
				}
				card.performAction(player, null, game);

			}

		}
	}

	@Test
	public void testAttack() {
		ArrayList<User> users = new ArrayList<User>();

		try {
			users.add(new User(String.valueOf(1), null));
			users.add(new User(String.valueOf(2), null));
		} catch (RemoteException e) {
		}

		Game game = new Game(users);
		game.run();
		Deck deck = game.getDeck();
		deck.reset();
		Player player = null;
		for (User user : users) {
			if (user.getPlayer() instanceof HumanPlayer) {
				player = user.getPlayer();
				boolean found = false;
				Card card = null;
				for (int i = 0; !found && i < ItemDeckFactory.MAX_ITEM_CARDS; i++) {
					card = deck.drawItemCard();
					if (card instanceof AttackItemCard) {
						found = true;
					}
				}
				card.performAction(player, null, game);

			}
		}

	}

	@Test
	public void testTeleport() {
		ArrayList<User> users = new ArrayList<User>();

		try {
			users.add(new User(String.valueOf(1), null));
			users.add(new User(String.valueOf(2), null));
		} catch (RemoteException e) {
		}

		Game game = new Game(users);
		game.run();
		Deck deck = game.getDeck();
		deck.reset();
		Player player = null;
		for (User user : users) {
			if (user.getPlayer() instanceof HumanPlayer) {
				player = user.getPlayer();
				boolean found = false;
				Card card = null;
				for (int i = 0; !found && i < ItemDeckFactory.MAX_ITEM_CARDS; i++) {
					card = deck.drawItemCard();
					if (card instanceof TeleportItemCard) {
						found = true;
					}
				}
				card.performAction(player, null, game);
				assertTrue(user.getPlayer().getPosition() instanceof HumanSector);
			}

		}
	}
}
