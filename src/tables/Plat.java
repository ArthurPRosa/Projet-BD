package tables;

import demo.Demonstrator;

import java.util.ArrayList;
import java.util.HashSet;

public class Plat {
    private String emailRest;
    private String nomPlat;
    private int prix;
    private String descPlat;

    public static void parseList() {
        // TODO afficher les plats depuis la bdd
        // TODO affichage par restaurant
    }

    public static void parseAdd() {
        Plat plat = new Plat().emailRest(Demonstrator.readConsole("Entrez l'email du restaurant proposant ce plat :"))
                .nomPlat(Demonstrator.readConsole("Entrez le nom du plat :"))
                .prix(Integer.parseInt(Demonstrator.readConsole("Entrez le prix du plat :")))
                .descPlat(Demonstrator.readConsole("Entrez la description du plat"));
        System.out.println(plat);
    }

    public static void parseDel() {
        // TODO afficher les plats et permettre à l'utilisateur d'en supprimer un
    }
    private Plat emailRest(String emailRest) {
        this.emailRest = emailRest;
        return this;
    }

    public Plat nomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
        return this;
    }

    public Plat prix(int prix) {
        this.prix = prix;
        return this;
    }

    public Plat descPlat(String descPlat) {
        this.descPlat = descPlat;
        return this;
    }

    @Override
    public String toString() {
        return "Plat{" +
                "emailRest='" + emailRest + '\'' +
                ", nomPlat='" + nomPlat + '\'' +
                ", prix=" + prix +
                ", descPlat='" + descPlat + '\'' +
                '}';
    }
}
