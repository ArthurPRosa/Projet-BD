package tables;

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
    }

    public static void parseAdd() {
    }

    public static void parseDel() {
    }

    public Categorie nomCat(String nomCat) {
        this.nomCat = nomCat;
        return this;
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
