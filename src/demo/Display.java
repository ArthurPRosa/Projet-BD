package demo.BD.demo;

import java.util.LinkedList;

/**
 * Class displaying stuff on the terminal
 */
public class Display {
    public static void presentation() {
        System.out.println("Bienvenue dans le démonstrateur du projet BD du groupe 5 (ISI1).");
    }

    public static void helper() {
        System.out.println("\n#################################\n");
        System.out.println("Faites help pour un récap des commandes disponibles, help <commande> pour un détail plus précis.");
        System.out.println("Si vous avez terminé d'entrer vos données, entrez la commande apply");
    }

    public static void displayList(LinkedList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "> " + list.get(i));
        }
    }

    public static void displayHelp(LinkedList<String> commands) {
        System.out.println("Liste des commandes disponibles :");
        displayList(commands);
    }
}
