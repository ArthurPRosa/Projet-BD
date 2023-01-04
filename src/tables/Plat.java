package tables;

import static java.lang.Math.min;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import demo.Console;

public class Plat {
    private static int count = 0;
    private static boolean firstRowPrinted = true;
    private String emailRest;
    private String nomPlat;
    private int prix;
    private String descPlat;

    /**
     * JavaDoc de Test...
     */
    public static void parseList() {
        try {
            PreparedStatement stmt = demo.Database.getDb().prepareStatement
                    ("SELECT nomPlat, prix, descPlat FROM Plat WHERE emailRest LIKE ?");
            String emailRest = Console.read("Entrez l'email du restaurant : ");
            stmt.setString(1, emailRest);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                Plat plat = new Plat()
                        .nomPlat(rset.getString(1))
                        .prix(Integer.parseInt(rset.getString(2)))
                        .descPlat(rset.getString(3));
                System.out.println(plat);
            }
            firstRowPrinted = true;
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    public static ArrayList<String> parseList(String emailRest) {
        try {
            ArrayList<String> plats = new ArrayList<String>();
            PreparedStatement stmt = demo.Database.getDb().prepareStatement
                    ("SELECT nomPlat, prix, descPlat FROM Plat WHERE emailRest LIKE ?");
            stmt.setString(1, emailRest);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                Plat plat = new Plat()
                        .nomPlat(rset.getString(1))
                        .prix(Integer.parseInt(rset.getString(2)))
                        .descPlat(rset.getString(3));
                System.out.println(plat);
                plats.add(count, plat.getNomPlat());
                return plats;
            }
            firstRowPrinted = true;
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
        return null;
    }

    public static void parseAdd() {
        Plat plat = new Plat().emailRest(Console.read("Entrez l'email du restaurant proposant ce plat :"))
                .nomPlat(Console.read("Entrez le nom du plat :"))
                .prix(Integer.parseInt(Console.read("Entrez le prix du plat :")))
                .descPlat(Console.read("Entrez la description du plat"));
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

    public String getNomPlat() {
        return nomPlat;
    }

    @Override
    public String toString() {
        String strPrix = String.valueOf(prix);
        String strNom = count + ". " + nomPlat;

        int sizeNom = strNom.length();
        int sizePrix = strPrix.length();
        int sizeDesc = descPlat.length();

        StringBuilder retString = new StringBuilder();
        if (firstRowPrinted) {
            retString.append("╔").append(("═").repeat(92)).append("╗").append("\n")
                    .append("║").append((" ").repeat(42)).append("Plats").append((" ").repeat(45)).append("║").append("\n")
                    .append("╠").append(("═").repeat(32)).append("╤").append(("═").repeat(6)).append("╤").append(("═").repeat(52)).append("╣").append("\n")
                    .append(String.format("║ %-30s │", "Nom"))
                    .append(String.format(" %-4s ", "Prix"))
                    .append(String.format("│ %-50s ║", "Description"))
                    .append("\n")
                    .append("╠").append(("═").repeat(32)).append("╪")
                    .append(("═").repeat(6))
                    .append("╪").append(("═").repeat(52)).append("╣")
                    .append("\n");
            firstRowPrinted = false;
        }
        int i = 0;
        while (i * 30 < sizeNom
                || i * 4 < sizePrix
                || i * 50 < sizeDesc) {
            retString.append(String.format("║ " + "%-30s │", strNom.substring(min(i * 30, sizeNom), min((i + 1) * 30, sizeNom))))
                    .append(String.format(" %-4s ", strPrix.substring(min(i * 4, sizePrix), min((i + 1) * 4, sizePrix))))
                    .append(String.format("│ %-50s ║", descPlat.substring(min(i * 50, sizeDesc), min((i + 1) * 50, sizeDesc))))
                    .append("\n");
            i++;
        }

        retString.append("╟").append(("─").repeat(32)).append("┼")
                .append(("─").repeat(6))
                .append("┼").append(("─").repeat(52)).append("╢");
        count ++;

        return retString.toString();
    }
}
