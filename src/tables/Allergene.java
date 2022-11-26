package tables;

import demo.Console;

public class Allergene {
    private String nomallergene;
    public static void parseList() {
        // TODO récupérer la liste des allergènes depuis la bdd et l'afficher
        // TODO affichage par plat
    }

    public static void parseAdd() {
        Allergene allergene = new Allergene(Console.readConsole("Quel est le nom de l'allergène ?"));
        System.out.println(allergene);
    }

    public static void parseDel() {
        parseList();
        System.out.println("Entrez le nom de l'allergène à supprimer : ");
        // TODO permettre à l'utilisateur d'en choisir un
        // TODO le supprimer de la bdd
    }

    public Allergene(String nomallergene) {
        this.nomallergene = nomallergene;
    }


    @Override
    public String toString() {
        return "Allergene{" +
                "nomallergene='" + nomallergene + '\'' +
                '}';
    }
}
