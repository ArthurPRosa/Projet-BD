package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.aesh.readline.Readline;
import org.aesh.readline.ReadlineBuilder;
import org.aesh.readline.tty.terminal.TerminalConnection;
import org.aesh.terminal.tty.Signal;
import org.aesh.terminal.utils.Config;

public class Demonstrator {
	static Connection bd;
	static private Readline readline;
	static private TerminalConnection terminalConn;
	static private AtomicBoolean readlineAvailable = new AtomicBoolean(true);
	static private String lastInput;

	public static void main(String[] args) {
		Display.presentation();
		Database.connection();
		Display.helper();

		// Creating the shutdown hook to avoid having an unclosed connection
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("# Fermeture de la base de données.");
				try {
					Demonstrator.bd.close();
					terminalConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("# Merci d'avoir utilisé ce démonstrateur !");
				super.run();
			}
		});
		try {
			while (true) {
				if (!readlineAvailable.compareAndSet(true, false))
					throw new IllegalStateException();
				terminalConn = new TerminalConnection(new Console());
				readline.readline(terminalConn, ">", (s) -> {
					Thread th = new Thread(new Runnable() {
						@Override
						public void run() {
							Command.parseCommand(s);
						}
					});
					th.start();
					readlineAvailable.set(true);
				});
				while (!readlineAvailable.get())
					Thread.sleep(10);

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("# Trying to finish program anyway.");
		}
		// shutdown hook will take care of closing connection.
	}

	public static String readConsole() {
		if (!readlineAvailable.compareAndSet(true, false))
			throw new IllegalStateException();
		readline.readline(terminalConn, "<", (s) -> {
			lastInput = s;
			readlineAvailable.set(true);;
		});
		try {
			while (!readlineAvailable.get())
				Thread.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastInput;
	}

	public static class Console implements Consumer<org.aesh.terminal.Connection> {

		@Override
		public void accept(org.aesh.terminal.Connection connection) {
			readline = ReadlineBuilder.builder().enableHistory(true).build();
			// setting our own signal handler for ctrl-c signals, lets close if we get any
			connection.setSignalHandler(signal -> {
				if (signal == Signal.INT)
					connection.write(Config.getLineSeparator());
				connection.close();
				System.exit(0);
			});
			// lets open the connection to the terminal using this thread
			connection.openNonBlocking();
		}
	}
}
