package it.polimi.ingsw.cerridifebbo.view.cli;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphics;
import it.polimi.ingsw.cerridifebbo.model.AlienSector;
import it.polimi.ingsw.cerridifebbo.model.DangerousSector;
import it.polimi.ingsw.cerridifebbo.model.EscapeHatchSector;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.HumanSector;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.SecureSector;
import it.polimi.ingsw.cerridifebbo.model.SedativesItemCard;
import it.polimi.ingsw.cerridifebbo.model.User;

public class CLIGraphics extends Graphics {

	private static final String SECURE_SECTOR = "S";
	private static final String DANGEROUS_SECTOR = "D";
	private static final String ALIEN_SECTOR = "A";
	private static final String HUMAN_SECTOR = "H";
	private static final String ESCAPE_HATCH_SECTOR = "E";
	private static final String START_TURN_MESSAGE = "Start Turn";
	private static final String END_TURN_MESSAGE = "End Turn";

	private static final String CHOICE_ONE = "1";
	private static final String CHOICE_TWO = "2";
	private static final String CHOICE_THREE = "3";
	private static final String CHOICE_FOUR = "4";
	private static final String MOVE_OPTIONS = "What do you want to do?" + "\n"
			+ CHOICE_ONE + Move.ATTACK + "\n" + CHOICE_TWO + Move.MOVEMENT
			+ "\n" + CHOICE_THREE + Move.USEITEMCARD + "\n" + CHOICE_FOUR
			+ Move.FINISH;

	private static final String SECTOR_SELECTION = "Which sector?";
	private static final String CARD_SELECTION = "Which card?";

	@Override
	public void initialize(Map map, Player player) {
		printMap(map);
		printPlayer(player);
	}

	private void printMap(Map map) {
		for (int i = 0; i < Map.ROWMAP; i++) {
			for (int j = 0; j < Map.COLUMNMAP; j++) {
				Sector currentCell = map.getCell(i, j);
				if (j % 2 == 0) {
					if (currentCell != null) {
						System.out.print(printTypeOfSector(currentCell)
								+ currentCell.toString() + " ");
					} else {
						System.out.print(("      "));
					}
				}
			}
			System.out.println();
			System.out.print("      ");
			for (int j = 0; j < Map.COLUMNMAP; j++) {
				Sector currentCell = map.getCell(i, j);
				if (j % 2 == 0) {
					if (currentCell != null) {
						System.out.print(printTypeOfSector(currentCell)
								+ currentCell.toString() + " ");
					} else {
						System.out.print(("      "));
					}
				}
			}
			System.out.println();
		}
	}

	private void printPlayer(Player player) {
		System.out.println("player position: " + player.getPosition());
		printCardPlayer(player);

	}

	private String printTypeOfSector(Sector currentCell) {
		if (currentCell instanceof SecureSector) {
			return (SECURE_SECTOR + " ");
		} else if (currentCell instanceof DangerousSector) {
			return (DANGEROUS_SECTOR + " ");
		} else if (currentCell instanceof AlienSector) {
			return (ALIEN_SECTOR + " ");
		} else if (currentCell instanceof HumanSector) {
			return (HUMAN_SECTOR + " ");
		} else if (currentCell instanceof EscapeHatchSector) {
			return (ESCAPE_HATCH_SECTOR + " ");
		} else
			return null;
	}

	private void printCardPlayer(Player player) {
		System.out.print("player cards: ");
		int nCard = player.getOwnCards().size();
		if (nCard != 0) {
			for (int i = 0; i < nCard; i++) {
				System.out.print(player.getOwnCards().get(i) + " ");
			}
			System.out.println("");
		} else {
			System.out.println("no card!");
		}
	}

	public static void main(String args[]) {
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		users.add(new User(UUID.randomUUID()));
		Game game = new Game(null, users);
		game.run();
		users.get(0).getPlayer().addCard(new SedativesItemCard());
		CLIGraphics cli = new CLIGraphics();
		cli.initialize(game.getMap(), game.getUsers().get(0).getPlayer());
		cli.startTurn();
		System.out.println(cli.declareMove(game.getUsers().get(0).getPlayer()));
	}

	@Override
	public void sendMessage(String message) {
		System.out.println(message);
	}

	@Override
	public void startTurn() {
		System.out.println(START_TURN_MESSAGE);

	}

	@Override
	public void endTurn() {
		System.out.println(END_TURN_MESSAGE);

	}

	@Override
	public String declareMove(Player player) {
		String move = null;
		Scanner in = new Scanner(System.in);
		do {
			System.out.println(MOVE_OPTIONS);
			String line = in.next();
			if (line.equals(CHOICE_ONE)) {
				move = Move.ATTACK;
				break;
			}
			if (line.equals(CHOICE_TWO)) {
				move = Move.MOVEMENT;
				System.out.println(SECTOR_SELECTION);
				line = in.next();
				move = move + " " + line;
				break;
			}
			if (line.equals(CHOICE_THREE)) {
				move = Move.USEITEMCARD;
				System.out.println(CARD_SELECTION);
				printCardPlayer(player);
				line = in.next();
				move = move + " " + line;
				if (line.equals("Sedatives") || line.equals("Spotlight")) {
					System.out.println(SECTOR_SELECTION);
					line = in.next();
					move = move + " " + line;
				}
				break;
			}
			if (line.equals(CHOICE_FOUR)) {
				move = Move.FINISH;
				break;
			}
		} while (true);
		in.close();
		return move;
	}
}
