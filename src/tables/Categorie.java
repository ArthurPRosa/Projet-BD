package tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

import demo.Console;
import javax.swing.*;

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
        // TODO afficher la liste des catégories depuis la bdd
        try {
            HashSet<String> DiffCat = new HashSet<String>();
            PreparedStatement stmt = demo.Database.getDb().prepareStatement
                    ("SELECT * " +
                            "FROM rest " +
                            "WHERE email LIKE ?");
            System.out.println("Email du restaurant : ");
            Scanner scan = new Scanner(System.in);
            String email = scan.next();
            scan.nextLine();

            stmt.setString(1, email);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                for (int i = 0; i < 1; i++) {
                    String nomCat = res.getString(i);
                    while (!globalCategories.get(nomCat).equals(nomCat)) {
                        if (!DiffCat.contains(nomCat)) {
                            System.out.println(nomCat);
                            nomCat = globalCategories.get(nomCat);
                            DiffCat.add(nomCat);
                            continue;
                        }
                        break;
                    }
                }
            }
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
