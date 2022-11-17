package demo;

import java.util.regex.Pattern;

public class Commande {
	private static final Pattern guill = Pattern.compile("\".*\"");
	private static final Pattern acco = Pattern.compile("\\{.*\\}");

	public static void parseCommande(String cmd) {
		String[] args = cmd.split(" ");
		if (args.length == 0) {
			System.out.println("Aucune commande n'a été rentrée.");
			showHelp();
		} else if (args[0].equals("help")) {
			showHelp(args);
		} else if (args[0].startsWith("resta"))
			handleRestau(cmd, args);
	}

	private static void showHelp(String[] args) {
		if (args == null || args.length == 1)
			showHelp();
		else if (args[1].startsWith("resta")) { // restaurant
			System.out.println("Liste des commandes disponibles pour RESTAURANT:");
			System.out.println();
			System.out.println(
					"\tCREATE <mail> \"<nom>\" <telephone> \"<adresse>\" <horaires midi,soir> <places> \"<presentation>\" <livraison> <a emporter> <sur place>");
			System.out.println("\t\tmail : restau@example.com");
			System.out.println("\t\tnom : \"Au restaurant grenoblois\"");
			System.out.println(
					"\t\ttéléphone : à rentrer sans espaces et possiblement avec indicateur, 0476123456 ou +33476123456.");
			System.out.println(
					"\t\thoraires sous la forme hh:mm-hh:mm,hh:mm-hh:mm, avec le 2e créneau plus tard que le premier et les créneaux dans le bon sens (l'heure de départ est plus petite que l'heure d'arrivée).");
			System.out.println("\t\tplaces : nombre de places assises du restaurant, un nombre.");
			System.out.println("\t\tpresentation : texte de présentation entre guillemets.");
			System.out.println(
					"\t\tlivraison  a emporter  sur place : true ou false (ou 0 ou 1) pour chaque méthode, true indiquant que le restaurant peut fournir ce service.");
		}
	}

	private static void showHelp() {
		System.out.println("Liste des commandes disponibles :");
		System.out.println();
		System.out.println("RESTAURANT :");
		System.out.println(
				"\tTout ce qui a trait aux restaurants, y compris l'ajout, la suppression, l'ajout d'une catégorie...");
		System.out.println("Faites \"help restaurant\" pour plus d'informations.");
	}

	private static void handleRestau(String cmd, String[] args) {
		if (args.length == 1) 
			showHelp(new String[] { "help", "restau" });
		else if (args[1].equals("cree")) {
			if (args.length < 12) { // Pas assez d'argument
				System.out.println("Il manque des arguments !");
				showHelp(new String[] { "help", "restau" });
				return;
			}
			
			
		}

	}

}
