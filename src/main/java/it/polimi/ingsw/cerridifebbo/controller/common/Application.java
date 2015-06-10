package it.polimi.ingsw.cerridifebbo.controller.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Application {
	private static final Logger LOG = Logger.getLogger(Application.class.getName());

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

	public static void println(String message) {
		System.out.println(message);
	}

	public static void print(String message) {
		System.out.print(message);
	}

	public static void exception(Throwable e) {
		LOG.log(Level.WARNING, e.getMessage(), e);
	}

	public static void exception(Throwable e, String message) {
		println(message);
	}

	public static void log(Throwable e) {
		LOG.log(Level.WARNING, e.getMessage(), e);
	}

	public static String readLine(String format, Object... args) throws IOException {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		Application.println(String.format(format, args));

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		return br.readLine();
	}
}
