package tables;

import demo.Demonstrator;

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
        Evaluation evaluation = new Evaluation().dateEval(Demonstrator.readConsole("Entrez la date de l'évaluation"))
                .hEval(Demonstrator.readConsole("Entrez l'heure de l'évaluation"))
                .avis(Demonstrator.readConsole("Entrez votre avis :"))
                .note(Integer.parseInt(Demonstrator.readConsole("Entrez votre note :")));
        System.out.println(evaluation);
    }

    public static void parseDel() {
        parseList();
    }

    public int getNote() {
        return note;
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
        this.note = (note > 5) ? 5 : note;
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
