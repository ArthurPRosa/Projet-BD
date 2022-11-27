package tables;

import demo.Console;
import demo.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Horaires {
    private Jour jour;
    private String heureOuverture;
    private String heureFermeture;

    public static void parseList() {
        // TODO afficher les horaires contenues dans la bdd
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement
                    ("SELECT * FROM HORAIRE");
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                for (int i = 0; i < 1; i++) {
                    System.out.println(rset.getString(i));
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
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
