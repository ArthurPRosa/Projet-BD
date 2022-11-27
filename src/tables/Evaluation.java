package tables;

import demo.Console;

public class Evaluation {
    private String dateEval;
    private String hEval;
    private String avis;
    private int note;

    public static void parseList() {
        // TODO lister les évaluations depuis la base de données
        // TODO affichage par restaurant
        // TODO affichage par idCompte
    }

    public static void parseAdd() {
        Evaluation evaluation = new Evaluation().dateEval(Console.read("Entrez la date de l'évaluation"))
                .hEval(Console.read("Entrez l'heure de l'évaluation"))
                .avis(Console.read("Entrez votre avis :"))
                .note(Console.readWithParse("Entrez votre note :", Integer::parseInt));
        System.out.println(evaluation);
    }

    public static void parseDel() {
        parseList();
    }

    public Evaluation dateEval(String dateEval) {
        this.dateEval = dateEval;
        return this;
    }

    public Evaluation hEval(String hEval) {
        this.hEval = hEval;
        return this;
    }

    public Evaluation avis(String avis) {
        this.avis = avis;
        return this;
    }

    public Evaluation note(int note) {
        this.note = Math.min(note, 5);
        return this;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "dateEval='" + dateEval + '\'' +
                ", hEval='" + hEval + '\'' +
                ", avis='" + avis + '\'' +
                ", note=" + note +
                '}';
    }
}
