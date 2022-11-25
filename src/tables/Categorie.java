package tables;

import demo.Demonstrator;

import java.util.Objects;

public class Categorie {
    private String nomCat;
    private Categorie catMere = null;

    public Categorie(String nomCat, Categorie catMere) {
        this.nomCat = nomCat;
        this.catMere = catMere;
    }

    public Categorie(String nomCat) {
        this.nomCat = nomCat;
    }

    public static void parseList() {
        // TODO afficher la liste des catégories depuis la bdd
    }

    public static void parseAdd() {
        Categorie cat = new Categorie(Demonstrator.readConsole("Quel est le nom de la catégorie ?"));
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
        return nomCat.equals(categorie.nomCat) && catMere.equals(categorie.catMere);
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
