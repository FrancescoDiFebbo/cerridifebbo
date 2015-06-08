package it.polimi.ingsw.cerridifebbo.controller.common;

public class Application {

	private Application() {
		
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
	
	public static void println(String message){
		System.out.println(message);
	}
	
	public static void print(String message){
		System.out.print(message);
	}
	
	public static void exception(Throwable e){
		//cattura le eccezzioni ma se ne sbarazza
	}
}
