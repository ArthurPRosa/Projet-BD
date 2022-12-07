package tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

import demo.Console;

public class Categorie {
    private String nomCat;
    private Categorie catMere;
    public static HashMap<String, String> globalCategories = new HashMap<String, String>();

    public Categorie(String nomCat, Categorie catMere) {
        this.nomCat = nomCat;
        this.catMere = catMere;
        globalCategories.put(nomCat, catMere.getNomCat());
    }

    public Categorie getCatMere() {
        return catMere;
    }

    public String getNomCat() {
        return nomCat;
    }

    public Categorie(String nomCat) {
        this.nomCat = nomCat;
    }

    public static void parseList() {
        // afficher la liste des catégories depuis la bdd
        try {
            PreparedStatement stmt = demo.Database.getDb().prepareStatement
                    ("SELECT nomCategorie FROM EstCategorieDe WHERE emailRest LIKE ?");
            String emailRest = Console.read("Entrez l'email du restaurant : ");
            stmt.setString(1, emailRest);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                String nomCategorieFille = rset.getString(1);
                System.out.println(nomCategorieFille);
                affMere(nomCategorieFille);
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    public static void affMere(String nomCategorieFille) {
        try {
            PreparedStatement stmt = demo.Database.getDb().prepareStatement("SELECT nomCategorieMere FROM APourMere WHERE nomCategorieFille LIKE ?");
            stmt.setString(1, nomCategorieFille);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                String nomCategorieMere = rset.getString(1);
                System.out.println(nomCategorieMere);
                affMere(nomCategorieMere);
            }
            stmt.close();

        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }


    }

    public static void parseAdd() {
        Categorie cat = new Categorie(Console.read("Quel est le nom de la catégorie ?"));
        System.out.println(cat);
    }

    public static void parseDel() {
        parseList();
        // TODO permettre à l'utilisateur d'en choisir une
        // TODO la supprimer de la bdd
        // TODO réfléchir aux problèmes d'héritage si on supprime une mère qui a une mère
    }

    public Categorie catMere(Categorie catMere) {
        this.catMere = catMere;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return Objects.equals(nomCat, categorie.nomCat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomCat, catMere);
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "nomCat='" + nomCat + '\'' +
                ", catMere=" + catMere +
                '}';
    }
}
