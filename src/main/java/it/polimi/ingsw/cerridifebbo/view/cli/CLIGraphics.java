package it.polimi.ingsw.cerridifebbo.view.cli;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphics;
import it.polimi.ingsw.cerridifebbo.controller.common.Application;
import it.polimi.ingsw.cerridifebbo.model.AlienSector;
import it.polimi.ingsw.cerridifebbo.model.Card;
import it.polimi.ingsw.cerridifebbo.model.DangerousSector;
import it.polimi.ingsw.cerridifebbo.model.EscapeHatchSector;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.HumanPlayer;
import it.polimi.ingsw.cerridifebbo.model.HumanSector;
import it.polimi.ingsw.cerridifebbo.model.Map;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.Player;
import it.polimi.ingsw.cerridifebbo.model.Sector;
import it.polimi.ingsw.cerridifebbo.model.SecureSector;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CLIGraphics extends Graphics {

	private static final String RESET_COLOR = "\u001B[0m";
	private static final String SECURE_SECTOR = "\u001B[37m";
	private static final String DANGEROUS_SECTOR = "\u001B[31m";
	private static final String ALIEN_SECTOR = "\u001B[32m";
	private static final String HUMAN_SECTOR = "\u001B[34m";
	private static final String ESCAPE_HATCH_SECTOR_OK = "\u001B[33m";
	private static final String ESCAPE_HATCH_SECTOR_KO = "\u001B[35m";
	private static final String START_TURN_MESSAGE = "It's your turn";
	private static final String END_TURN_MESSAGE = "----------------------------------------";

	private static final String MOVE_OPTIONS_HUMAN = "What do you want to do?"
			+ "\n" + Move.MOVEMENT + "\n" + Move.USEITEMCARD + "\n"
			+ Move.FINISH;
	private static final String MOVE_OPTIONS_ALIEN = "What do you want to do?"
			+ "\n" + Move.ATTACK + "\n" + Move.MOVEMENT + "\n" + Move.FINISH;

	private static final String SECTOR_SELECTION = "Which sector?";
	private static final String CARD_SELECTION = "Which card?";
	private static final String USE_DISCARD = "Chose if you want use or discard a card";
	private static final String USE_CARD = "use";
	private static final String DELETE_CARD = "delete";
	private static final String CARD_PLAYER = "Player cards: ";
	private static final String NO_CARD_PLAYER = "No card!";
	private static final String NO_CARD_PLAYER_SELECTION = "You have no card to use!";
	private static final String PLAYER_POSITION = "Player position : ";
	private static final String PLAYER_RACE_HUMAN = "You are a human. Your name is ";
	private static final String PLAYER_RACE_ALIEN = "You are an alien. Your name is ";
	private static final String SECTOR_ESCAPE_UPDATE = " is not more available for escape";
	private Scanner in = new Scanner(System.in);
	private Player player;
	private Map map;
	private int turn = 0;
	private Timer timeout = new Timer();
	private Thread inputThread;

	@Override
	public void initialize(Map map, Player player, int numberOfPlayers) {
		this.player = player;
		this.map = map;
		printMap();
		printPlayer();
		if (numberOfPlayers == 2) {
			Application
					.println("You are not alone. There is a creature on the ship");
		} else {
			Application.println("You are not alone. There are "
					+ (numberOfPlayers - 1) + " creatures on the ship");
		}
		Application.println(END_TURN_MESSAGE);
		initialized = true;
	}

	private void printMap() {
		for (int i = 0; i < Map.COLUMNMAP; i = i + 2) {
			Application.print(" ___    ");
		}
		Application.println("");
		for (int i = 0; i < Map.ROWMAP; i++) {
			for (int j = 0; j < Map.COLUMNMAP; j = j + 2) {
				Sector currentCell = map.getCell(i, j);
				Application.print("/");
				if (currentCell != null) {
					Application.print(printTypeOfSector(currentCell)
							+ currentCell.toString() + RESET_COLOR);
				} else {
					Application.print("   ");

				}
				if (j != Map.COLUMNMAP - 1)
					Application.print("\\___");
			}
			Application.println("\\");
			for (int j = 1; j < Map.COLUMNMAP; j = j + 2) {
				Application.print("\\___/");
				Sector currentCell = map.getCell(i, j);
				if (currentCell != null) {
					Application.print(printTypeOfSector(currentCell)
							+ currentCell.toString() + RESET_COLOR);
				} else {
					Application.print("   ");
				}

			}
			Application.println("\\___/");
		}
		Application.print("    \\___/");
		for (int i = 3; i < Map.COLUMNMAP; i = i + 2) {
			Application.print("   \\___/");
		}
		Application.println("");

	}

	private void printPlayer() {
		if (player instanceof HumanPlayer) {
			Application.print(PLAYER_RACE_HUMAN);
		} else {
			Application.print(PLAYER_RACE_ALIEN);
		}
		Application.println(player.getPlayerCard().getCharacterName());
		printPlayerPosition();
		printCardPlayer();

	}

	private void printPlayerPosition() {
		Application.println(PLAYER_POSITION + player.getPosition());
	}

	private String printTypeOfSector(Sector currentCell) {
		if (currentCell instanceof SecureSector) {
			return SECURE_SECTOR;
		} else if (currentCell instanceof DangerousSector) {
			return DANGEROUS_SECTOR;
		} else if (currentCell instanceof AlienSector) {
			return ALIEN_SECTOR;
		} else if (currentCell instanceof HumanSector) {
			return HUMAN_SECTOR;
		} else if (currentCell instanceof EscapeHatchSector) {
			if (currentCell.isPassable()) {
				return ESCAPE_HATCH_SECTOR_OK;
			} else {
				return ESCAPE_HATCH_SECTOR_KO;
			}
		} else
			return null;
	}

	private void printCardPlayer() {
		Application.print(CARD_PLAYER);
		int nCard = player.getOwnCards().size();
		if (nCard != 0) {
			for (int i = 0; i < nCard; i++) {
				Application.print(player.getOwnCards().get(i) + " ");
			}
			Application.println("");
		} else {
			Application.println(NO_CARD_PLAYER);
		}
	}

	@Override
	public void sendMessage(String message) {
		Application.println(message);
	}

	@Override
	public void startTurn() {
		printMap();
		printPlayer();
		Application.println("TURN: " + ++turn);
		Application.println(START_TURN_MESSAGE);
		timeout = new Timer();
		timeout.schedule(new TimerTask() {
			@Override
			public void run() {
				inputThread.interrupt();
			}
		}, Game.MAX_TIMEOUT);
	}

	@Override
	public void endTurn() {
		Application.println(END_TURN_MESSAGE);
		timeout.cancel();
	}

	private void printOptions() {
		if (player instanceof HumanPlayer) {
			Application.println("\n" + MOVE_OPTIONS_HUMAN);
		} else {
			Application.println("\n" + MOVE_OPTIONS_ALIEN);
		}
	}

	@Override
	public void declareMove() {
		inputThread = new Thread() {
			@Override
			public void run() {
				try {
					boolean chosen = false;
					do {
						printOptions();
						String line = null;
						line = in.nextLine();
						line = line.replace(" ", "");
						if (line.equalsIgnoreCase(Move.ATTACK)) {
							getNetworkInterface().sendToServer(Move.ATTACK,
									null);
							chosen = true;
						} else if (line.equalsIgnoreCase(Move.MOVEMENT)) {
							Application.println(SECTOR_SELECTION);
							line = in.nextLine();
							line = line.replace(" ", "");
							getNetworkInterface().sendToServer(Move.MOVEMENT,
									line);
							chosen = true;
						} else if (line.equalsIgnoreCase(Move.USEITEMCARD)) {
							if (!player.getOwnCards().isEmpty()) {
								printCardPlayer();
								Application.println(CARD_SELECTION);
								line = in.nextLine();
								line = line.replace(" ", "");
								getNetworkInterface().sendToServer(
										Move.USEITEMCARD, line);
								chosen = true;
							} else {
								Application.println(NO_CARD_PLAYER_SELECTION);
							}
						} else if (line.equalsIgnoreCase(Move.FINISH)) {
							getNetworkInterface().sendToServer(Move.FINISH,
									null);
							chosen = true;
						}
					} while (!chosen);
				} catch (Exception e) {
					Application.exception(e,
							"You didn't make your decision in time", false);
				}

			}
		};
		inputThread.start();
	}

	@Override
	public void declareSector() {
		inputThread = new Thread() {
			@Override
			public void run() {
				try {
					Application.println(SECTOR_SELECTION);
					String move = null;
					move = in.nextLine();
					move = move.replace(" ", "");
					getNetworkInterface().sendToServer(Move.SECTOR, move);
				} catch (Exception e) {
					Application.exception(e,
							"You didn't make your decision in time", false);
				}

			}
		};
		inputThread.start();
	}

	@Override
	public void updatePlayerPosition(Player player) {
		this.player = player;
	}

	@Override
	public void declareCard() {
		inputThread = new Thread() {
			@Override
			public void run() {
				try {
					printCardPlayer();
					boolean chosen = false;
					do {
						Application.print(USE_DISCARD);
						String line = null;
						line = in.nextLine();
						line = line.replace(" ", "");
						if (line.equalsIgnoreCase(USE_CARD)) {
							chosen = true;
							Application.println(CARD_SELECTION);
							String move = null;
							move = in.nextLine();
							move = move.replace(" ", "");
							getNetworkInterface().sendToServer(
									Move.USEITEMCARD, move);
						} else if (line.equalsIgnoreCase(DELETE_CARD)) {
							chosen = true;
							Application.println(CARD_SELECTION);
							String move = in.nextLine();
							move = move.replace(" ", "");
							getNetworkInterface().sendToServer(Move.DELETECARD,
									move);
						}
					} while (!chosen);
				} catch (Exception e) {
					Application.exception(e,
							"You didn't make your decision in time", false);
				}
			}
		};
		inputThread.start();
	}

	@Override
	public void deletePlayerCard(Player player, Card card) {
		this.player = player;
	}

	@Override
	public void addPlayerCard(Player player, Card card) {
		this.player = player;
	}

	@Override
	public void updateEscapeHatch(Map map, Sector sector) {
		this.map = map;
		sendMessage(sector.toString() + SECTOR_ESCAPE_UPDATE);
	}
}
