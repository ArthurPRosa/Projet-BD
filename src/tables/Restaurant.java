package tables;

import java.util.ArrayList;
import java.util.HashSet;

public class Restaurant {
    private String email;
    private String nom;
    private int numTel;
    private String adr;
    private HashSet<Categorie> catRestau = new HashSet<Categorie>();
    private Horaires horaires;
    private int nbPlace;
    private String textPres;
    private ArrayList<Type> typeComm = new ArrayList<Type>();
    private ArrayList<Evaluation> evals = new ArrayList<Evaluation>();
    private double note;

    public Restaurant(String email) {
        this.email = email;
    }

    public static void parseList() {
    }

    public static void parseAdd() {
    }

    public static void parseDel() {
    }

    public Restaurant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public Restaurant numTel(int numTel) {
        this.numTel = numTel;
        return this;
    }

    public Restaurant adr(String adr) {
        this.adr = adr;
        return this;
    }

    public Restaurant catRestau(ArrayList<Categorie> catRestau) {
        for (Categorie categorie : catRestau) {
            this.catRestau.add(categorie);
        }
        return this;
    }

    public Restaurant catRestau(Categorie catRestau) {
        this.catRestau.add(catRestau);
        return this;
    }

    public Restaurant horaires(Horaires horaires) {
        this.horaires = horaires;
        return this;
    }

    public Restaurant nbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
        return this;
    }

    public Restaurant textPres(String textPres) {
        this.textPres = textPres;
        return this;
    }

    public Restaurant typeComm(Type typeComm) {
        this.typeComm.add(typeComm);
        return this;
    }

    public Restaurant evals(ArrayList<Evaluation> evals) {
        for (Evaluation eval : evals) {
            this.evals.add(eval);
        }
        return this;
    }

    public Restaurant evals(Evaluation eval) {
        this.evals.add(eval);
        return this;
    }

    public Restaurant note() {
        double nMoyenne = 0;
        for (Evaluation eval : this.evals) {
            nMoyenne += eval.getNote();
        }
        nMoyenne /= this.evals.size();
        this.note = nMoyenne;
        return this;
    }

    public enum Type {
        LIVRAISON, A_EMPORTER, SUR_PLACE
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", numTel=" + numTel +
                ", adr='" + adr + '\'' +
                ", catRestau=" + catRestau +
                ", horaires=" + horaires +
                ", nbPlace=" + nbPlace +
                ", textPres='" + textPres + '\'' +
                ", typeComm=" + typeComm +
                ", evals=" + evals +
                ", note=" + note +
                '}';
    }
}
