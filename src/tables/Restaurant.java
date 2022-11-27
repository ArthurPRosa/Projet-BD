package tables;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import demo.Console;
import demo.Database;

public class Restaurant {
    private static String emailRest;
    private static String nomRest;
    private static int telRest;
    private static String adresseRest;
    private static String presentation;
    private static int capaciteMax;
    private double noteRest;
    private static HashSet<Categorie> nomCategories = new HashSet<Categorie>();

    public Restaurant(String email) {
        emailRest = email;
    }

    public static void parseList() {
        // TODO récupérer les restaurants depuis la bdd et les afficher
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement
                    ("SELECT * " +
                            "FROM RESTAURANT R, POSSEDEHORAIRES P, ESTDELACATEGORIE E " +
                            "WHERE R.emailRest = P.emailRest AND R.emailRest = E.emailRest");
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
        Restaurant rest = new Restaurant(Console.read("Quel est le mail du restaurant ?"))
                .nom(Console.read("Quel est le nom du restaurant ?"))
                .numTel(Console.readWithParse("Quel est son numéro de téléphone", Integer::parseInt))
                .adr(Console.read("Quel est son adresse ?"))
                .nbPlace(Console.readWithParse("Combien de places possède-t-il ?", Integer::parseInt))
                .textPres(Console.read("Entrez un texte de présentation :"));
        // TODO catégories
        // TODO horaires
        System.out.println(rest);
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement
                    ("INSERT INTO RESTAURANT VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?");
            stmt.setString(1, emailRest);
            stmt.setString(2, nomRest);
            stmt.setInt(3, telRest);
            stmt.setString(4, adresseRest);
            stmt.setString(5, presentation);
            stmt.setInt(6, capaciteMax);
            stmt.setInt(7, 0);
            stmt.setArray(8, (Array) nomCategories);

            stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    public static void parseDel() {
        // TODO récupérer les éléments depuis la bdd et les afficher
        // TODO permettre à l'utilisateur d'en choisir un
        // TODO le supprimer de la bdd

    }

    public Restaurant nom(String nom) {
        nomRest = nom;
        return this;
    }

    public Restaurant numTel(int numTel) {
        telRest = numTel;
        return this;
    }

    public Restaurant adr(String adr) {
        adresseRest = adr;
        return this;
    }

    public Restaurant catRestau(ArrayList<Categorie> catRestau) {
        nomCategories.addAll(catRestau);
        return this;
    }

    public Restaurant catRestau(Categorie catRestau) {
        nomCategories.add(catRestau);
        return this;
    }

    public Restaurant nbPlace(int nbPlace) {
        capaciteMax = nbPlace;
        return this;
    }

    public Restaurant textPres(String textPres) {
        presentation = textPres;
        return this;
    }

    public Restaurant note() {
        // TODO calculer la note selon les évaluations du restaurant
        return this;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "emailRest='" + emailRest + '\'' +
                ", nomRest='" + nomRest + '\'' +
                ", telRest=" + telRest +
                ", adresseRest='" + adresseRest + '\'' +
                ", presentation='" + presentation + '\'' +
                ", capaciteMax=" + capaciteMax +
                ", noteRest=" + noteRest +
                ", nomCategories=" + nomCategories +
                '}';
    }
}
