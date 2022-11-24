package src.tables;

import java.util.ArrayList;
import java.util.HashSet;

public class Plat {
    private int num;
    private String nom;
    private String desc;
    private int prix;
    private HashSet<String> lAllerg;

    public Plat(int num) {
        this.num = num;
    }

    public static void parseList() {
    }

    public static void parseAdd() {
    }

    public static void parseDel() {
    }

    public Plat numero(int num) {
        this.num = num;
        return this;
    }

    public Plat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public Plat desc(String desc) {
        this.desc = desc;
        return this;
    }

    public Plat prix(int prix) {
        this.prix = prix;
        return this;
    }

    public Plat lAllerg(ArrayList<String> lAllerg) {
        for (String all : lAllerg) {
            this.lAllerg.add(all);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "num=" + num +
                ", nom='" + nom + '\'' +
                ", desc='" + desc + '\'' +
                ", prix=" + prix +
                ", lAllerg=" + lAllerg +
                '}';
    }
}
