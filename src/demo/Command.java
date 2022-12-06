package demo;

import tables.*;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Class handling commands typed in the terminal
 */
public class Command {
	public static LinkedList<String> commands = new LinkedList<>(
			Arrays.asList("help", "list", "order", "rate", "populate", "create", "drop", "forget", "exit", "quit"));

	public static void parseCommand(String cmd) {
		String[] args = cmd.split(" ");
		switch (args[0]) {
			case "help":
				Display.displayCommandList(commands);
				break;
			case "list":
				switch (args[1]) {
					case "rest":
						if (args.length < 3)
							Restaurant.parseList();
						else
							switch (args[2]) {
								case "cat" -> {
								}
								case "hor" -> {
									Restaurant.parseListDateFiltered();
								}
							}
						break;
					case "dish":
						Plat.parseList();
						break;
					case "cat":
						Categorie.parseList();
						break;
					case "users":
						Client.parseList();
						break;
				}
				break;
			case "rate":
				Evaluation.parseAdd();
				break;
			case "order":
				Commande.parseOrder();
				break;
			case "log":
				Client.parseConnexion();
				break;
			case "populate":
				Database.populate();
				break;
			case "create":
				Database.createTables();
				break;
			case "drop":
				Database.deleteTables();
				break;
			case "forget":
				Client.forget();
				break;
			case "quit":
			case "exit":
				System.exit(0);
		}
	}

	private static void apply() {
		// TODO
	}

}
