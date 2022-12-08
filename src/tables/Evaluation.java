package tables;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import demo.Console;
import demo.Database;

public class Evaluation {
    private String dateEval;
    private int hEval;
    private String avis;
    private int note;

    public static void parseList() {
        // TODO lister les évaluations depuis la base de données
        // TODO affichage par restaurant
        // TODO affichage par idCompte
    }

    public static void parseAdd() {
        Evaluation evaluation = new Evaluation().dateEval(Console.read("Entrez la date de l'évaluation"))
                .hEval(Console.readWithParse("Entrez l'heure de l'évaluation", Integer::parseInt))
                .avis(Console.read("Entrez votre avis :"))
                .note(Console.readWithParse("Entrez votre note :", Integer::parseInt));
        System.out.println(evaluation);
        try {
            // TODO set autocommit off;
            PreparedStatement stmt = Database.getDb().prepareStatement("INSERT INTO Eval VALUES (?, ?, ?, ?)");
            stmt.setString(1, evaluation.dateEval);
            stmt.setInt(2, evaluation.hEval);
            stmt.setString(3, evaluation.avis);
            stmt.setInt(4, evaluation.note);
            stmt.executeQuery();
            stmt.close();
            stmt = Database.getDb().prepareStatement("INSERT INTO PossedeEvaluation VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            // TODO lien avec la commande
            stmt.setString(5, evaluation.dateEval);
            stmt.setInt(6, evaluation.hEval);
            stmt.setString(7, evaluation.avis);
            stmt.setInt(8, evaluation.note);
            stmt.executeQuery();
            stmt.close();
            // TODO commit
            // TODO set autocommit on
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    public static void parseDel() {
        parseList();
    }

    public Evaluation dateEval(String dateEval) {
        this.dateEval = dateEval;
        return this;
    }

    public Evaluation hEval(int hEval) {
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
