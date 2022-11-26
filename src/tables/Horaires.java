package tables;

import demo.Console;

public class Horaires {
    private Jour jour;
    private String heureOuverture;
    private String heureFermeture;

    public static void parseList() {
        // TODO afficher les horaires contenues dans la bdd
    }

    public static void parseAdd() {
        Horaires horaires = new Horaires().jour(Jour.valueOf(Console.readConsole("Entrez le jour de la semaine : ")))
                .heureOuverture(Console.readConsole("Entrez l'heure d'ouverture"))
                .heureFermeture(Console.readConsole("Entrez l'heure de fermeture"));
    }

    public static void parseDel() {
        // TODO supprimer un horaire
    }

    public enum Jour {
        LUNDI,
        MARDI,
        MERCREDI,
        JEUDI,
        VENDREDI,
        SAMEDI,
        DIMANCHE
    }

    public Horaires jour(Jour jour) {
        this.jour = jour;
        return this;
    }

    public Horaires heureOuverture(String heureOuverture) {
        this.heureOuverture = heureOuverture;
        return this;
    }

    public Horaires heureFermeture(String heureFermeture) {
        this.heureFermeture = heureFermeture;
        return this;
    }
}
