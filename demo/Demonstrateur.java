package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Demonstrateur {
	static Connection bd;

	public static void main(String[] args) {
		System.out.println("Bienvenue dans le démonstrateur du projet BD du groupe 5 (ISI1).");
		try {
			System.out.println("Importation du driver...");
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("Connection à la base de données...");
			bd = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1", "eyraudh", "eyraudh");
			System.out.println("Connection réussie !");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println();
		System.out.println("--------------------");
		System.out.println();
		System.out.println(
				"Faites help pour un récap des commandes disponibles, help <commande> pour un détail plus précis.");
		System.out.println();
		System.out.println(
				"Exemple : restaurant cree <mail> <nom> <telephone> \"<adresse>\" <horaires midi,soir> <places> "
						+ "\"<presentation>\" <livraison> <a emporter> <sur place>.");
		System.out.println();
		System.out.println("Apres avoir indiqué les modifications désirées, il faut faire la commande apliquer");
		System.out.println("exit pour sortir.");

		Scanner sc = new Scanner(System.in);

		// Creating the shutdown hook to avoid having an unclosed connection
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("Fermeture de la base de données.");
				sc.close();
				try {
					Demonstrateur.bd.close();
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
				Commande.parseCommande(entry);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Trying to finish program anyway.");
		}
		// shutdown hook will take care of closing connection.
	}
}