package tables;

import java.util.ArrayList;
import java.util.Date;

public class Horaires {
    private ArrayList<ArrayList<Date>> horJours;

    public Horaires() {
        this.horJours = new ArrayList<ArrayList<Date>>(7);
    }

    public static void parseList() {
    }

    public static void parseAdd() {
    }

    public static void parseDel() {

    }

    public void AddHor(Jour jour, Date horOuv, Date horFerm, Periode periode) {
        horJours.get(jour.getNum()).set(periode.getNum(), horOuv);
        horJours.get(jour.getNum()).set(periode.getNum() + 1, horFerm);
    }

    @Override
    public String toString() {
        return "Horaires{" +
                "horJours=" + horJours +
                '}';
    }

    public enum Jour {
        LUNDI(0),
        MARDI(1),
        MERCREDI(2),
        JEUDI(3),
        VENDREDI(4),
        SAMEDI(5),
        DIMANCHE(6);
        private int numJ = 0;

        Jour(int numJ) {
            this.numJ = numJ;
        }

        public int getNum() {
            return numJ;
        }
    }

    public enum Periode {
        MIDI(0),
        SOIR(2);
        private int numH = 0;

        Periode(int numH) {
            this.numH = numH;
        }

        public int getNum() {
            return numH;
        }
    }
}
