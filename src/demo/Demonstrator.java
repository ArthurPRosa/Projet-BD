package demo;

import java.sql.SQLException;

public class Demonstrator {

	public static void main(String[] args) {
		Display.presentation();
		Database.connection();
		Display.helper();

		Console.init();

		// Creating the shutdown hook to avoid having an unclosed connection
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("# Fermeture de la base de données.");
				try {
					Database.close();
					Console.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("# Merci d'avoir utilisé ce démonstrateur !");
				super.run();
			}
		});

		Console.listenCommands();
		// shutdown hook will take care of closing connection.
	}

}
