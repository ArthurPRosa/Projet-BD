package tables;

import demo.Console;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Plat {
    private String emailRest;
    private String nomPlat;
    private int prix;
    private String descPlat;

    public static void parseList() {
        // TODO afficher les plats depuis la bdd
        // TODO affichage par restaurant
        try {
            PreparedStatement stmt = demo.Database.getDb().prepareStatement
                    ("SELECT * FROM EstCategorieDe WHERE emailRest LIKE ?");
            String emailRest = Console.read("Entrez l'email du restaurant : ");
            stmt.setString(1, emailRest);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                Plat plat = new Plat()
                        .emailRest(rset.getString(1))
                        .nomPlat(rset.getString(2))
                        .prix(Integer.parseInt(rset.getString(3)))
                        .descPlat(rset.getString(4));
                System.out.println(plat);
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
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
