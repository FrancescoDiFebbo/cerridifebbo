package it.polimi.ingsw.cerridifebbo.controller.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class Util provides methods for printing on system.out, exiting, and
 * exception handling.
 *
 * @author cerridifebbo
 */
public class Util {

	/** The logger. */
	private static final Logger LOG = Logger.getLogger(Util.class.getName());

	/**
	 * Instantiates a new util.
	 */
	private Util() {

	}

	/**
	 * Exit error.
	 */
	public static void exitError() {
		exit(-1);
	}

	/**
	 * Exit success.
	 */
	public static void exitSuccess() {
		exit(0);
	}

	/**
	 * Exit.
	 *
	 * @param e
	 *            the exception
	 */
	public static void exit(Throwable e) {
		exception(e);
		exitError();
	}

	/**
	 * Exit.
	 *
	 * @param e
	 *            the exception
	 * @param message
	 *            the message
	 */
	public static void exit(Throwable e, String message) {
		exception(e, message);
		exitError();
	}

	/**
	 * Exit.
	 *
	 * @param exitStatus
	 *            the exit status
	 */
	private static void exit(int exitStatus) {
		println("Closing...");
		System.exit(exitStatus);
	}

	/**
	 * Prints a String and then terminate the line.
	 *
	 * @param message
	 *            the message
	 */
	public static void println(String message) {
		System.out.println(message);
	}

	/**
	 * Prints a string.
	 *
	 * @param message
	 *            the message
	 */
	public static void print(String message) {
		System.out.print(message);
	}

	/**
	 * Shows the exception in logger.
	 *
	 * @param e
	 *            the e
	 */
	public static void exception(Throwable e) {
		LOG.log(Level.WARNING, e.getMessage(), e);
	}

	/**
	 * Shows a message and the exception message surrounded by parenthesis.
	 *
	 * @param e
	 *            the e
	 * @param message
	 *            the message
	 */
	public static void exception(Throwable e, String message) {
		println(message + " (" + e.getMessage() + ")");
	}

	/**
	 * Read line from console.
	 *
	 * @param format
	 *            the format
	 * @param args
	 *            the args
	 * @return the string
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static String readLine(String format, Object... args) throws IOException {
		if (System.console() != null) {
			return System.console().readLine(format, args);
		}
		Util.println(String.format(format, args));

		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		return br.readLine();
	}
}
