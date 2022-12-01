package demo;

import tables.*;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Class handling commands typed in the terminal
 */
public class Command {
	private static LinkedList<String> commands = new LinkedList<>(
			Arrays.asList("help", "list", "add", "del", "order", "populate", "create", "drop", "forget", "exit", "quit"));

	public static void parseCommand(String cmd) {
		String[] args = cmd.split(" ");
		switch (args[0]) {
			case "help":
				Display.displayCommandList(commands);
				break;
			case "list":
				switch (args[1]) {
					case "rest" -> Restaurant.parseList();
					case "dish" -> Plat.parseList();
					case "aller" -> Allergene.parseList();
					case "cat" -> Categorie.parseList();
					case "users" -> Client.parseList();
					case "order" -> Commande.parseList();
					case "rating" -> Evaluation.parseList();
				}
				break;
			case "add":
				if ("rating".equals(args[1])) {
					Evaluation.parseAdd();
				}
				break;
			case "del":
				switch (args[1]) {
					case "order" -> Commande.parseDel();
					case "rating" -> Evaluation.parseDel();
				}
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
			// exit command is treating inside demonstrator
		}
	}

	private static void apply() {
		// TODO
	}

}
