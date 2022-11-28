package demo;

import java.util.Arrays;
import java.util.LinkedList;

import tables.Allergene;
import tables.Categorie;
import tables.Client;
import tables.Commande;
import tables.Evaluation;
import tables.Horaires;
import tables.Plat;
import tables.Restaurant;

/**
 * Class handling commands passed onto the terminal
 */
public class Command {
	private static LinkedList<String> commands = new LinkedList<>(
			Arrays.asList("list", "add", "del", "exit", "quit", "help", "order"));

	public static void parseCommand(String cmd) {
		String[] args = cmd.split(" ");
		switch (args[0]) {
			case "help":
				Display.displayHelp(commands);
				break;
			case "list":
				switch (args[1]) {
					case "rest" -> Restaurant.parseList();
					case "sched" -> Horaires.parseList();
					case "dish" -> Plat.parseList();
					case "aller" -> Allergene.parseList();
					case "cat" -> Categorie.parseList();
					case "users" -> Client.parseList();
					case "order" -> Commande.parseList();
					case "rating" -> Evaluation.parseList();
				}
				break;
			case "add":
				switch (args[1]) {
					case "rest" -> Restaurant.parseAdd();
					case "sched" -> Horaires.parseAdd();
					case "dish" -> Plat.parseAdd();
					case "aller" -> Allergene.parseAdd();
					case "cat" -> Categorie.parseAdd();
					case "users" -> Client.parseAdd();
					case "order" -> Commande.parseAdd();
					case "rating" -> Evaluation.parseAdd();
				}
				break;
			case "del":
				switch (args[1]) {
					case "rest" -> Restaurant.parseDel();
					case "sched" -> Horaires.parseDel();
					case "dish" -> Plat.parseDel();
					case "aller" -> Allergene.parseDel();
					case "cat" -> Categorie.parseDel();
					case "users" -> Client.parseDel();
					case "order" -> Commande.parseDel();
					case "rating" -> Evaluation.parseDel();
				}
				break;
			case "order":
				Commande.parseAdd();
			// exit command is treating inside demonstrator
		}
	}

	/*
	 * private static void showHelp(String[] args) {
	 * if (args == null || args.length == 1)
	 * demo.BD.demo.Display.displayHelp(commands);
	 * else if (args[1].startsWith("resta")) { // restaurant
	 * System.out.println("Liste des commandes disponibles pour RESTAURANT:");
	 * System.out.println();
	 * System.out.println(
	 * "\tCREATE <mail> \"<nom>\" <telephone> \"<adresse>\" <horaires midi,soir> <places> \"<presentation>\" <livraison> <a emporter> <sur place>"
	 * );
	 * System.out.println("\t\tmail : restau@example.com");
	 * System.out.println("\t\tnom : \"Au restaurant grenoblois\"");
	 * System.out.println(
	 * "\t\ttéléphone : à rentrer sans espaces et possiblement avec indicateur, 0476123456 ou +33476123456."
	 * );
	 * System.out.println(
	 * "\t\thoraires sous la forme hh:mm-hh:mm,hh:mm-hh:mm, avec le 2e créneau plus tard que le premier et les créneaux dans le bon sens (l'heure de départ est plus petite que l'heure d'arrivée)."
	 * );
	 * System.out.
	 * println("\t\tplaces : nombre de places assises du restaurant, un nombre.");
	 * System.out.
	 * println("\t\tpresentation : texte de présentation entre guillemets.");
	 * System.out.println(
	 * "\t\tlivraison  a emporter  sur place : true ou false (ou 0 ou 1) pour chaque méthode, true indiquant que le restaurant peut fournir ce service."
	 * );
	 * }
	 * }
	 */

	private static void apply() {
		// TODO
	}

}
