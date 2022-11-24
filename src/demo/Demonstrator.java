package src.demo;

import java.sql.SQLException;
import java.util.Scanner;

public class Demonstrator {


	public static void main(String[] args) {

		Display.presentation();
		Database.connection();
		Display.helper();

		Scanner sc = new Scanner(System.in);

		// Creating the shutdown hook to avoid having an unclosed connection
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Fermeture de la base de données.");
				sc.close();
				try {
					Database.getBd().close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println("Merci d'avoir utilisé ce démonstrateur !");
				super.run();
			}
		});

		try {
			while (true) {
				System.out.print(">");
				if (!sc.hasNextLine())
					break;
				String entry = sc.nextLine();
				if (entry.equals("exit") || entry.equals("quit")) {
					break;
				}
				Command.parseCommand(entry);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Trying to finish program anyway.");
		}
		// shutdown hook will take care of closing connection.
	}
}