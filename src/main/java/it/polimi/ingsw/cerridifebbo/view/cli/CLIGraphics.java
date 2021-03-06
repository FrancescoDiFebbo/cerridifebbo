package it.polimi.ingsw.cerridifebbo.view.cli;

import it.polimi.ingsw.cerridifebbo.controller.client.Graphics;
import it.polimi.ingsw.cerridifebbo.controller.common.ItemCardRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.MapRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.PlayerRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.SectorRemote;
import it.polimi.ingsw.cerridifebbo.controller.common.Util;
import it.polimi.ingsw.cerridifebbo.model.AlienPlayer;
import it.polimi.ingsw.cerridifebbo.model.AlienSector;
import it.polimi.ingsw.cerridifebbo.model.DangerousSector;
import it.polimi.ingsw.cerridifebbo.model.EscapeHatchSector;
import it.polimi.ingsw.cerridifebbo.model.Game;
import it.polimi.ingsw.cerridifebbo.model.HumanSector;
import it.polimi.ingsw.cerridifebbo.model.Move;
import it.polimi.ingsw.cerridifebbo.model.SecureSector;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class describes a CLI Graphics.
 * 
 * @see Graphics that is implemented by this classs
 * @author cerridifebbo
 *
 */
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
	private static final String DANGEROUS = DangerousSector.class.getSimpleName();
	private static final String SECURE = SecureSector.class.getSimpleName();
	private static final String HATCH = EscapeHatchSector.class.getSimpleName();
	private static final String ALIEN = AlienSector.class.getSimpleName();
	private static final String HUMAN = HumanSector.class.getSimpleName();
	private static final String MOVE_OPTIONS = "What do you want to do?";
	private static final String MOVE_ATTACK = Move.ATTACK;
	private static final String MOVE_USEITEMCARD = Move.USEITEMCARD;
	private static final String MOVE_FINISH = Move.FINISH;
	private static final String MOVE_MOVEMENT = Move.MOVEMENT;
	private static final String ALIEN_CLASS = AlienPlayer.class.getSimpleName();
	private static final String SECTOR_SELECTION = "Which sector?";
	private static final String CARD_SELECTION = "Which card?";
	private static final String USE_DISCARD = "Chose if you want use or discard a card";
	private static final String USE_CARD = "use";
	private static final String DELETE_CARD = "delete";
	private static final String CARD_PLAYER = "Player cards: ";
	private static final String NO_CARD_PLAYER = "No card!";
	private static final String PLAYER_POSITION = "Player position : ";
	private static final String PLAYER_RACE_HUMAN = "You are a human. Your name is ";
	private static final String PLAYER_RACE_ALIEN = "You are an alien. Your name is ";
	private static final String SECTOR_ESCAPE_UPDATE = " is not more available for escape";
	private static final String EXCEPTION_MESSAGE = "Remember that the turn time is not unlimited!";
	private Scanner in = new Scanner(System.in);
	private PlayerRemote player;
	private MapRemote map;
	private int turn = 0;
	private Timer timeout = new Timer();
	private Thread inputThread;
	private boolean chosen = false;

	/**
	 * This method initializes the graphics.It prints the map and the player and
	 * also a brief start game message.
	 * 
	 * @author cerridifebbo
	 * @param map
	 *            the map of the specific game
	 * @param player
	 *            the player used by the client
	 * @param numberOfPlayers
	 *            the number of player of the specific game
	 */
	@Override
	public void initialize(MapRemote map, PlayerRemote player, int numberOfPlayers) {
		this.player = player;
		this.map = map;
		printMap();
		printPlayer();
		if (numberOfPlayers == 2) {
			Util.println("You are not alone. There is a creature on the ship");
		} else {
			Util.println("You are not alone. There are " + (numberOfPlayers - 1) + " creatures on the ship");
		}
		Util.println(END_TURN_MESSAGE);
		initialized = true;
	}

	/**
	 * This method prints the map.
	 * 
	 * @author cerridifebbo
	 */
	private void printMap() {
		for (int i = 0; i < MapRemote.COLUMNMAP; i = i + 2) {
			Util.print(" ___    ");
		}
		Util.println("");
		for (int i = 0; i < MapRemote.ROWMAP; i++) {
			for (int j = 0; j < MapRemote.COLUMNMAP; j = j + 2) {
				SectorRemote currentCell = map.getCell(i, j);
				Util.print("/");
				if (currentCell != null) {
					Util.print(printTypeOfSector(currentCell) + currentCell.getCoordinate() + RESET_COLOR);
				} else {
					Util.print("   ");

				}
				if (j != MapRemote.COLUMNMAP - 1)
					Util.print("\\___");
			}
			Util.println("\\");
			for (int j = 1; j < MapRemote.COLUMNMAP; j = j + 2) {
				Util.print("\\___/");
				SectorRemote currentCell = map.getCell(i, j);
				if (currentCell != null) {
					Util.print(printTypeOfSector(currentCell) + currentCell.getCoordinate() + RESET_COLOR);
				} else {
					Util.print("   ");
				}

			}
			Util.println("\\___/");
		}
		Util.print("    \\___/");
		for (int i = 3; i < MapRemote.COLUMNMAP; i = i + 2) {
			Util.print("   \\___/");
		}
		Util.println("");

	}

	/**
	 * This method prints the player info.
	 * 
	 * @author cerridifebbo
	 */
	private void printPlayer() {
		if (player.getRace().equals(ALIEN_CLASS)) {
			Util.print(PLAYER_RACE_ALIEN);
		} else {
			Util.print(PLAYER_RACE_HUMAN);
		}
		Util.println(player.getPlayerCard().getName());
		printPlayerPosition();
		printCardPlayer();

	}

	/**
	 * This method prints the player position.
	 * 
	 * @author cerridifebbo
	 */
	private void printPlayerPosition() {
		Util.println(PLAYER_POSITION + player.getPos());
	}

	/**
	 * This method chooses and return the color of the parameter currentCell
	 * (ANSI Color: String). The type of currentCell determines the returned
	 * color.
	 * 
	 * @author cerridifebbo
	 * @param currentCell
	 *            the sector that is analyzed
	 */
	private String printTypeOfSector(SectorRemote currentCell) {
		if (currentCell.getType().equals(SECURE)) {
			return SECURE_SECTOR;
		} else if (currentCell.getType().equals(DANGEROUS)) {
			return DANGEROUS_SECTOR;
		} else if (currentCell.getType().equals(ALIEN)) {
			return ALIEN_SECTOR;
		} else if (currentCell.getType().equals(HUMAN)) {
			return HUMAN_SECTOR;
		} else if (currentCell.getType().equals(HATCH)) {
			if (currentCell.isPassable()) {
				return ESCAPE_HATCH_SECTOR_OK;
			} else {
				return ESCAPE_HATCH_SECTOR_KO;
			}
		} else
			return null;
	}

	/**
	 * This method prints the cards of the player.
	 * 
	 * @author cerridifebbo
	 */
	private void printCardPlayer() {
		Util.print(CARD_PLAYER);
		int nCard = player.getOwnCards().size();
		if (nCard != 0) {
			for (int i = 0; i < nCard; i++) {
				Util.print(player.getOwnCards().get(i).getName() + " ");
			}
			Util.println("");
		} else {
			Util.println(NO_CARD_PLAYER);
		}
	}

	/**
	 * This method prints the parameter message.
	 * 
	 * @author cerridifebbo
	 * @param message
	 *            the message that will be displayed
	 */
	@Override
	public void sendMessage(String message) {
		Util.println(message);
	}

	/**
	 * This method prepares all the staff for start the player's turn.It prints
	 * the map and the player's info. It also run a timer that indicates the
	 * maximum player's time turn. It also prints a START_TURN_MESSAGE.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void startTurn() {
		printMap();
		printPlayer();
		Util.println("TURN: " + ++turn);
		Util.println(START_TURN_MESSAGE);
		timeout = new Timer();
		timeout.schedule(new TimerTask() {
			@Override
			public void run() {
				inputThread.interrupt();
				chosen = true;
			}
		}, Game.MAX_TIMEOUT);
	}

	/**
	 * This method prepares all the staff for end the player's turn. It prints a
	 * END_TURN_MESSAGE.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void endTurn() {
		Util.println(END_TURN_MESSAGE);
		timeout.cancel();
		inputThread.interrupt();
		chosen = true;
	}

	/**
	 * This method prints the move options of the player.
	 * 
	 * @author cerridifebbo
	 */
	private void printOptions() {
		Util.println("\n" + MOVE_OPTIONS);
		Util.println(MOVE_MOVEMENT);
		if (player.getRace().equals(ALIEN_CLASS)) {
			Util.println(MOVE_ATTACK);
		} else if (!player.getOwnCards().isEmpty()) {
			Util.println(MOVE_USEITEMCARD);
		}
		Util.println(MOVE_FINISH);
	}

	/**
	 * This method prepares all the staff for prepare the player to declare a
	 * move. It prints the move options and wait the user's command. If the
	 * start turn finish or if there is an exception the thread that is open
	 * when the client try to insert a command will be interrupted.
	 * 
	 * @author cerridifebbo
	 * @throws Exception
	 *             when the scanner or the thread have some problems.
	 */
	@Override
	public void declareMove() {
		inputThread = new Thread(new DeclareMoveRunnable());
		inputThread.start();
	}

	/**
	 * This method prepares all the staff for prepare the player to declare a
	 * sector.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void declareSector() {
		inputThread = new Thread(new DeclareSectorRunnable());
		inputThread.start();
	}

	/**
	 * This method update the position of the player. It also prints the player
	 * position.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just changed the position
	 */
	@Override
	public void updatePlayerPosition(PlayerRemote player) {
		this.player = player;
		printPlayerPosition();
	}

	/**
	 * This method prepare all the staff for prepare the player to declare a
	 * card.
	 * 
	 * @author cerridifebbo
	 */
	@Override
	public void declareCard() {
		inputThread = new Thread(new DeclareCardRunnable());
		inputThread.start();
	}

	/**
	 * This method update the cards of the player. It also prints the player
	 * cards.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just deleted a card
	 * @param card
	 *            the card that has just been deleted
	 */
	@Override
	public void deletePlayerCard(PlayerRemote player, ItemCardRemote card) {
		this.player = player;
		printCardPlayer();
	}

	/**
	 * This method update the cards of the player. It also prints the player
	 * cards.
	 * 
	 * @author cerridifebbo
	 * @param player
	 *            the player that has just added a card
	 * @param card
	 *            the card that has just been added
	 */
	@Override
	public void addPlayerCard(PlayerRemote player, ItemCardRemote card) {
		this.player = player;
	}

	/**
	 * This method update the status of the parameter sector of the parameter
	 * map.
	 * 
	 * @author cerridifebbo
	 * @param map
	 *            the current map of the game
	 * @param sector
	 *            the sector that has just been modified
	 */
	@Override
	public void updateEscapeHatch(MapRemote map, SectorRemote sector) {
		this.map = map;
		sendMessage(sector.getCoordinate() + SECTOR_ESCAPE_UPDATE);
	}

	/**
	 * This class implements Runnable.
	 * 
	 * @author cerridifebbo
	 *
	 */
	private class DeclareMoveRunnable implements Runnable {

		/**
		 * This method prepares all the staff for prepare the player to declare
		 * a move. It prints the move options and wait the user's command. If
		 * the start turn finish or if there is an exception the thread that is
		 * open when the client try to insert a command will be interrupted.
		 * 
		 * @author cerridifebbo
		 * @throws Exception
		 *             when the scanner or the thread have some problems.
		 */
		@Override
		public void run() {
			try {
				chosen = false;
				do {
					printOptions();
					String line = null;
					line = in.nextLine();
					line = line.replace(" ", "");
					if (line.equalsIgnoreCase(Move.ATTACK)) {
						getNetworkInterface().sendToServer(Move.ATTACK, null);
						chosen = true;
					} else if (line.equalsIgnoreCase(Move.MOVEMENT)) {
						Util.println(SECTOR_SELECTION);
						line = in.nextLine();
						line = line.replace(" ", "");
						getNetworkInterface().sendToServer(Move.MOVEMENT, line);
						chosen = true;
					} else if (line.equalsIgnoreCase(Move.USEITEMCARD)) {
						printCardPlayer();
						Util.println(CARD_SELECTION);
						line = in.nextLine();
						line = line.replace(" ", "");
						getNetworkInterface().sendToServer(Move.USEITEMCARD, line);
						chosen = true;
					} else if (line.equalsIgnoreCase(Move.FINISH)) {
						getNetworkInterface().sendToServer(Move.FINISH, null);
						chosen = true;
					}
				} while (!chosen);
			} catch (Exception e) {
				Util.exception(e, EXCEPTION_MESSAGE);
			}
		}
	}

	/**
	 * This class implements Runnable.
	 * 
	 * @author cerridifebbo
	 *
	 */
	private class DeclareSectorRunnable implements Runnable {

		/**
		 * This method prepares all the staff for prepare the player to declare
		 * a sector. It prints SECTOR_SELECTION and wait the user's command. If
		 * the start turn finish or if there is an exception the thread that is
		 * open when the client try to insert a command will be interrupted.
		 * 
		 * @author cerridifebbo
		 * @throws Exception
		 *             when the scanner or the thread have some problems.
		 */
		@Override
		public void run() {
			try {
				Util.println(SECTOR_SELECTION);
				String move = null;
				move = in.nextLine();
				move = move.replace(" ", "");
				getNetworkInterface().sendToServer(Move.SECTOR, move);
			} catch (Exception e) {
				Util.exception(e, EXCEPTION_MESSAGE);

			}

		}
	}

	/**
	 * This class implements Runnable.
	 * 
	 * @author cerridifebbo
	 *
	 */
	private class DeclareCardRunnable implements Runnable {

		/**
		 * This method prepares all the staff for prepare the player to declare
		 * a card. It prints USE_DISCARD and wait the user's command. If the
		 * start turn finish or if there is an exception the thread that is open
		 * when the client try to insert a command will be interrupted.
		 * 
		 * @author cerridifebbo
		 * @throws Exception
		 *             when the scanner or the thread have some problems.
		 */
		@Override
		public void run() {
			try {
				printCardPlayer();
				chosen = false;
				do {
					Util.print(USE_DISCARD);
					String line = null;
					line = in.nextLine();
					line = line.replace(" ", "");
					if (line.equalsIgnoreCase(USE_CARD)) {
						chosen = true;
						Util.println(CARD_SELECTION);
						String move = null;
						move = in.nextLine();
						move = move.replace(" ", "");
						getNetworkInterface().sendToServer(Move.USEITEMCARD, move);
					} else if (line.equalsIgnoreCase(DELETE_CARD)) {
						chosen = true;
						Util.println(CARD_SELECTION);
						String move = in.nextLine();
						move = move.replace(" ", "");
						getNetworkInterface().sendToServer(Move.DELETECARD, move);
					}
				} while (!chosen);
			} catch (Exception e) {
				Util.exception(e, EXCEPTION_MESSAGE);
			}

		}
	}
}
