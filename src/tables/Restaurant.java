package tables;

import static java.lang.Math.min;
import static tables.Categorie.affFilleRet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import demo.Console;
import demo.Database;

public class Restaurant {
    private static boolean firstRowPrinted = true;
    private static DecimalFormat df = new DecimalFormat("##.##");
    private static String emailRest;
    private static String nomRest;
    private static int telRest;
    private static String adresseRest;
    private static String presentation;
    private static int capaciteMax;
    private double noteRest;

    public Restaurant(String email) {
        emailRest = email;
    }

    public static void parseList() {
        // récupérer les restaurants depuis la bdd et les afficher
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement
                    ("SELECT * " +
                            "FROM Restaurant R,  EstCategorieDe E " +
                            "WHERE R.emailRest = E.emailRest");
            ResultSet rset = stmt.executeQuery();
            System.out.println("informations restau");
            while (rset.next()) {
                Restaurant rest = new Restaurant((rset.getString(1)));
                nomRest = rset.getString(2);
                telRest = rset.getInt(3);
                adresseRest = rset.getString(4);
                presentation = rset.getString(5);
                capaciteMax = rset.getInt(6);
                System.out.println(rest);
            }
            firstRowPrinted = true;
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    public static void parseListCat() {
        String cat = Console.read("Entrez la catégorie recherchée : ");
        PriorityQueue<String> emailsRest = parseListCatRec(cat, new PriorityQueue<String>(new RestComparator()));
        for (String email : emailsRest) {
            System.out.println(email);
        }
    }

    public static void parseListCat(String mail) {
        PriorityQueue<String> emailsRest = parseListCatRec(mail, new PriorityQueue<String>(new RestComparator()));
        for (String email : emailsRest) {
            System.out.println(email);
        }
    }

    public static PriorityQueue<String> parseListCatRec(String nomCatMere, PriorityQueue<String> listRestCat) {
        // récupérer les restaurants depuis la bdd et les afficher
        try {
            PreparedStatement stmt = demo.Database.getDb().prepareStatement
                    ("SELECT R.emailRest FROM Restaurant R, EstCategorieDe D " +
                            "WHERE R.emailRest = D.emailRest AND D.nomCategorie = ?");
            stmt.setString(1, nomCatMere);
            ResultSet rset = stmt.executeQuery();
            while (rset.next()) {
                String emailRest = rset.getString(1);
                listRestCat.add(emailRest);
            }
            ArrayList<String> cats = affFilleRet(nomCatMere);
            for (int i = 1; i < cats.size(); i++) {
                parseListCatRec(cats.get(i), listRestCat);
            }
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
        return listRestCat;
    }

    public static void parseListDateFiltered() {
        //Horaires.Jour jour = Console.readWithParse("Entrez le jour de la semaine : ", Horaires.Jour::parse);
        String jour = Console.read("Entrez le jour de la semaine : ");
        String heure = Console.read("Entrez l'heure d'ouverture");

        try {
            PreparedStatement stmt = Database.getDb().prepareStatement
                    ("SELECT * " +
                            "FROM RESTAURANT R, POSSEDEHORAIRES P" +
                            "WHERE R.emailRest = P.emailRest AND P.jour = ?" +
                            "AND P.heureOuverture <= ? AND P.heureFermeture >= ?");
            stmt.setString(0, jour);
            stmt.setString(1, heure);
            stmt.setString(2, heure);
            ResultSet rset = stmt.executeQuery();

            while (rset.next()) {
                for (int i = 0; i < 4; i++) {
                    System.out.println(rset.getString(i));
                }
            }
            firstRowPrinted = true;
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    public static void parseAdd() {
        Restaurant rest = new Restaurant(Console.read("Quel est le mail du restaurant ?"))
                .nom(Console.read("Quel est le nom du restaurant ?"))
                .numTel(Console.readWithParse("Quel est son numéro de téléphone", Integer::parseInt))
                .adr(Console.read("Quel est son adresse ?"))
                .nbPlace(Console.readWithParse("Combien de places possède-t-il ?", Integer::parseInt))
                .textPres(Console.read("Entrez un texte de présentation :"));
        // TODO catégories
        // TODO horaires
        System.out.println(rest);
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement
                    ("INSERT INTO RESTAURANT VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, emailRest);
            stmt.setString(2, nomRest);
            stmt.setInt(3, telRest);
            stmt.setString(4, adresseRest);
            stmt.setString(5, presentation);
            stmt.setInt(6, capaciteMax);
            stmt.executeQuery();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    public static void parseDel() {
        // TODO récupérer les éléments depuis la bdd et les afficher
        // TODO permettre à l'utilisateur d'en choisir un
        // TODO le supprimer de la bdd

    }

    public Restaurant nom(String nom) {
        nomRest = nom;
        return this;
    }

    public Restaurant numTel(int numTel) {
        telRest = numTel;
        return this;
    }

    public Restaurant adr(String adr) {
        adresseRest = adr;
        return this;
    }

    public Restaurant nbPlace(int nbPlace) {
        capaciteMax = nbPlace;
        return this;
    }

    public Restaurant textPres(String textPres) {
        presentation = textPres;
        return this;
    }

    public float note() {
        // calculer la note selon les évaluations du restaurant
        int sum = 0;
        int c = 1;
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement("SELECT note FROM PossedeEvaluation WHERE emailRest LIKE ?");
            stmt.setString(1, emailRest);
            ResultSet rset = stmt.executeQuery();
            sum = 0;
            c = 1;
            int count = 0;
            while (rset.next()) {
                sum += rset.getInt(1);
                c = (count == 0) ? 1 : ++c;
                count++;
            }

            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
        return (float) (sum / c);
    }


    @Override
    public String toString() {
        String strTelRest = String.valueOf(telRest);
        String strCapMax = String.valueOf(capaciteMax);
        String strNote = String.valueOf(df.format(noteRest));

        int sizeEmail = emailRest.length();
        int sizenomRest = nomRest.length();
        int sizetelRest = strTelRest.length();
        int sizeAdrRest = adresseRest.length();
        int sizePresentation = presentation.length();
        int sizeCapMax = strCapMax.length();
        int sizeNote = strNote.length();

        StringBuilder retString = new StringBuilder();
        if (firstRowPrinted) {
            retString.append("╔").append(("═").repeat(160)).append("╗").append("\n")
                    .append("║").append((" ").repeat(71)).append("Restaurants").append((" ").repeat(78)).append("║").append("\n")
                    .append("╠").append(("═").repeat(32)).append("╤").append(("═").repeat(29)).append("╤").append(("═").repeat(11)).append("╤").append(("═").repeat(27)).append("╤").append(("═").repeat(42)).append("╤").append(("═").repeat(6)).append("╤").append(("═").repeat(7)).append("╣").append("\n")
                    .append(String.format("║ %-30s │", "Email"))
                    .append(String.format(" %-27s ", "Nom"))
                    .append(String.format("│ %-9s │", "Tel"))
                    .append(String.format(" %-25s ", "Adresse"))
                    .append(String.format("│ %-40s │", "Presentation"))
                    .append(String.format(" %-4s ", "Cap"))
                    .append(String.format("│ %-5s ║", "Note"))
                    .append("\n")
                    .append("╠").append(("═").repeat(32)).append("╪")
                    .append(("═").repeat(29))
                    .append("╪").append(("═").repeat(11)).append("╪")
                    .append(("═").repeat(27))
                    .append("╪").append(("═").repeat(42)).append("╪")
                    .append(("═").repeat(6))
                    .append("╪").append(("═").repeat(7)).append("╣")
                    .append("\n");
            firstRowPrinted = false;
        }
        int i = 0;
        while (i * 30 < sizeEmail
                || i * 27 < sizenomRest
                || i * 9 < sizetelRest
                || i * 25 < sizeAdrRest
                || i * 40 < sizePresentation
                || i * 4 < sizeCapMax
                || i * 5 < sizeNote) {
            retString.append(String.format("║ %-30s │", emailRest.substring(min(i * 30, sizeEmail), min((i + 1) * 30, sizeEmail))))
                    .append(String.format(" %-27s ", nomRest.substring(min(i * 27, sizenomRest), min((i + 1) * 27, sizenomRest))))
                    .append(String.format("│ %-9s │", strTelRest.substring(min(i * 9, sizetelRest), min((i + 1) * 9, sizetelRest))))
                    .append(String.format(" %-25s ", adresseRest.substring(min(i * 25, sizeAdrRest), min((i + 1) * 25, sizeAdrRest))))
                    .append(String.format("│ %-40s │", presentation.substring(min(i * 40, sizePresentation), min((i + 1) * 40, sizePresentation))))
                    .append(String.format(" %-4s ", strCapMax.substring(min(i * 4, sizeCapMax), min((i + 1) * 4, sizeCapMax))))
                    .append(String.format("│ %-5s ║", strNote.substring(min(i * 5, sizeNote), min((i + 1) * 5, sizeNote))))
                    .append("\n");
            i++;
        }

        retString.append("╟").append(("─").repeat(32)).append("┼")
                .append(("─").repeat(29))
                .append("┼").append(("─").repeat(11)).append("┼")
                .append(("─").repeat(27))
                .append("┼").append(("─").repeat(42)).append("┼")
                .append(("─").repeat(6))
                .append("┼").append(("─").repeat(7)).append("╢");
        return retString.toString();
    }
}

class RestComparator implements Comparator<String> {

    public float note(String email) {
        // calculer la note selon les évaluations du restaurant
        int sum = 0;
        int c = 1;
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement("SELECT note FROM PossedeEvaluation WHERE emailRest LIKE ?");
            stmt.setString(1, email);
            ResultSet rset = stmt.executeQuery();
            sum = 0;
            c = 1;
            int count = 0;
            while (rset.next()) {
                sum += rset.getInt(1);
                c = (count == 0) ? 1 : ++c;
                count++;
            }

            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
        return (float) (sum / c);
    }

    public int compare(String emailRest1, String emailRest2) {
        float note1 = note(emailRest1);
        float note2 = note(emailRest2);
        if (note1 < note2)
            return 1;
        else if (note1 > note2)
            return -1;
        return 0;
    }
}
