package demo.BD.tables;

public class Evaluation {
    private String dateEval;
    private String hEval;
    private String avis;
    private int note;

    public static void parseList() {
    }

    public static void parseAdd() {
    }

    public static void parseDel() {

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
