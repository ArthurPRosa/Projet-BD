package tables;

import java.util.ArrayList;
import java.util.HashSet;

import demo.Console;

public class Restaurant {
    private String emailRest;
    private String nomRest;
    private int telRest;
    private String adresseRest;
    private String presentation;
    private int capaciteMax;
    private double noteRest;
    private HashSet<Categorie> nomCategories = new HashSet<Categorie>();

    public Restaurant(String email) {
        this.emailRest = email;
    }

    public static void parseList() {
        // TODO récupérer les restaurants depuis la bdd et les afficher

    }

    public static void parseAdd() {
        Restaurant rest = new Restaurant(Console.readConsole("Quel est le mail du restaurant ?"))
                .nom(Console.readConsole("Quel est le nom du restaurant ?"))
                .numTel(Integer.parseInt(Console.readConsole("Quel est son numéro de téléphone ?")))
                .adr(Console.readConsole("Quel est son adresse ?"))
                .nbPlace(Integer.parseInt(Console.readConsole("Combien de places possède-t-il ?")))
                .textPres(Console.readConsole("Entrez un texte de présentation :"));
                // TODO catégories
        System.out.println(rest);
    }

    public static void parseDel() {
        // TODO récupérer les éléments depuis la bdd et les afficher
        // TODO permettre à l'utilisateur d'en choisir un
        // TODO le supprimer de la bdd
    }

    public Restaurant nom(String nom) {
        this.nomRest = nom;
        return this;
    }

    public Restaurant numTel(int numTel) {
        this.telRest = numTel;
        return this;
    }

    public Restaurant adr(String adr) {
        this.adresseRest = adr;
        return this;
    }

    public Restaurant catRestau(ArrayList<Categorie> catRestau) {
        this.nomCategories.addAll(catRestau);
        return this;
    }

    public Restaurant catRestau(Categorie catRestau) {
        this.nomCategories.add(catRestau);
        return this;
    }

    public Restaurant nbPlace(int nbPlace) {
        this.capaciteMax = nbPlace;
        return this;
    }

    public Restaurant textPres(String textPres) {
        this.presentation = textPres;
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
