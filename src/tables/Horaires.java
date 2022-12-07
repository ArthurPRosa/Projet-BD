package tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import demo.Console;
import demo.Database;

public class Horaires {
    private Jour jour;
    private String heureOuverture;
    private String heureFermeture;

    public static void parseList() {
        // TODO afficher les horaires contenues dans la bdd
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement("SELECT * FROM HORAIRE");
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
        Horaires horaires = new Horaires().jour(Console.readWithParse("Entrez le jour de la semaine : ",Jour::parse))
                .heureOuverture(Console.read("Entrez l'heure d'ouverture"))
                .heureFermeture(Console.read("Entrez l'heure de fermeture"));
        System.out.println(horaires);
        try {
            // TODO
            PreparedStatement stmt = Database.getDb().prepareStatement
                    ("");
            ResultSet rset = stmt.executeQuery();


        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
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
        DIMANCHE;

        public static Jour parse(String s) throws ParseException{
            try {
                return Jour.valueOf(s);
            } catch (IllegalArgumentException e) {
                for (Jour j : Jour.values()) {
                    if (s.equalsIgnoreCase(j.name()))
                        return j;
                }
                try {
                    int index = Integer.parseInt(s);
                    if (index >= 0 && index < Jour.values().length)
                        return Jour.values()[index];
                } catch (NumberFormatException e2) {
                }
            }
            System.out.println("Veuillez rentrer un jour valide");
            throw new ParseException(s, 0);
        }
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
