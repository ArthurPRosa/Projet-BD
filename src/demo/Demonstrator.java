package demo;

import org.aesh.readline.Readline;
import org.aesh.readline.ReadlineBuilder;
import org.aesh.readline.tty.terminal.TerminalConnection;
import org.aesh.terminal.tty.Signal;
import org.aesh.terminal.utils.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class Demonstrator {

	static private Readline readline;
	static private TerminalConnection terminalConn;
	static private AtomicBoolean readlineAvailable = new AtomicBoolean(true);
	static private AtomicBoolean inParsing = new AtomicBoolean(false);
	static private String lastInput;
	private static Connection db;

	public static void main(String[] args) {
		Display.presentation();
		connection();
		Display.helper();

		// Creating the shutdown hook to avoid having an unclosed connection
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("# Fermeture de la base de données.");
				try {
					Demonstrator.db.close();
					terminalConn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("# Merci d'avoir utilisé ce démonstrateur !");
				super.run();
			}
		});
		try {
			terminalConn = new TerminalConnection(new Console());
			while (true) {
				while (!readlineAvailable.compareAndSet(true, false)) {
				}
				readline.readline(terminalConn, ">", (s) -> {
					Thread th = new Thread(new Runnable() {
						@Override
						public void run() {
							inParsing.set(true);
							Command.parseCommand(s);
							inParsing.set(false);
						}
					});
					th.start();
					readlineAvailable.set(true);
				});
				while (!readlineAvailable.get() || inParsing.get())
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
			readlineAvailable.set(true);
		});
		try {
			while (!readlineAvailable.get())
				Thread.sleep(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lastInput;
	}

	public static String readConsole(String prompt) {
		System.out.println(prompt);
		return readConsole();
	}

	public static class Console implements Consumer<org.aesh.terminal.Connection> {

		@Override
		public void accept(org.aesh.terminal.Connection connection) {
			readline = ReadlineBuilder.builder().enableHistory(false).build();
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

	public static void connection() {
		try {
			System.out.println("Importation du driver...");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("Connexion à la base de données...");
			db = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1", "eyraudh", "eyraudh");
			System.out.println("Connection réussie !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
