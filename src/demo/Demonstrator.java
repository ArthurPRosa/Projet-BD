package demo;

public class Demonstrator {

	public static void main(String[] args) {
		Display.presentation();
		Database.connection();
		Display.helper();
		Database.createTables();
		Database.deleteTables();

		Console.init();

		// Creating the shutdown hook to avoid having an unclosed connection
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("# Fermeture de la base de données.");
				Database.closeConnection();
				Console.close();
				System.out.println("# Merci d'avoir utilisé ce démonstrateur !");
				super.run();
			}
		});

		Console.prompt();
		// shutdown hook will take care of closing connection.
	}

}
