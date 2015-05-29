package it.polimi.ingsw.cerridifebbo.controller.server;

public class Application {

	private Application() {
		// TODO Auto-generated constructor stub
	}

	public static void exitError() {
		exit(-1);
	}

	public static void exitSuccess() {
		exit(0);
	}

	private static void exit(int exitStatus) {
		System.exit(exitStatus);
	}
}
