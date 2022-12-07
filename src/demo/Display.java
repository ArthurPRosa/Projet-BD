package demo;

import java.util.LinkedList;

/**
 * Class displaying stuff on the terminal
 */
public class Display {
    /**
     * Display presentation text
     */
    public static void presentation() {
        System.out.println("Bienvenue dans le démonstrateur du projet BD du groupe 5 (ISI1).");
    }

    /**
     * Display help text
     */
    public static void helper() {
        System.out.println("\n#################################\n");
        System.out.println("Faites help pour un récap des commandes disponibles, help <commande> pour un détail plus précis.");
    }

    /**
     * Display a list in the terminal, one item at the time
     * @param list LinkedLists to display
     */
    public static void displayList(LinkedList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + "> " + list.get(i));
        }
    }

    /**
     * Display the list of available commands to the user
     * @param commands LinkedList of commands available
     */
    public static void displayCommandList(LinkedList<String> commands) {
        System.out.println("Liste des commandes disponibles :");
        displayList(commands);
    }
}
