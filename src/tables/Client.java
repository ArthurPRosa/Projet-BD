package tables;

import demo.Console;
import demo.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static java.lang.Math.min;
import static tables.Restaurant.parseListCatRec;

public class Client {
    private String emailClient;
    private String mdp;
    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private static int currentIdCompte = -1;
    private static HashMap<Integer, String> recommandations = new HashMap<Integer, String>();
    private static boolean firstRowPrinted = true;

    public static void forget() {
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement("DELETE FROM Client WHERE idCompte=?");
            stmt.setInt(1, currentIdCompte);
            stmt.executeQuery();
            stmt.close();
            currentIdCompte = -1;
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    public Client(String emailClient) {
        this.emailClient = emailClient;
    }

    public static void parseList() {
        // lister les clients depuis la bdd
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement("SELECT * " +
                    "FROM Client R,  Compte C " +
                    "WHERE R.idCompte = C.idCompte");
            ResultSet rset = stmt.executeQuery();
            System.out.println("informations restau");
            while (rset.next()) {
                Client client = new Client(rset.getString(1));
                client.mdp = rset.getString(2);
                client.nomClient = rset.getString(3);
                client.prenomClient = rset.getString(4);
                client.adresseClient = rset.getString(5);
                currentIdCompte = rset.getInt(6);
                System.out.println(client);
            }
            firstRowPrinted = true;
            stmt.close();
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    /*  */
    public static boolean parseConnexion() {
        String adresseMail = Console.read("Entrez votre adresse mail : ");

        /* ------- */
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement("SELECT mdp FROM Client WHERE emailClient = ?");
            stmt.setString(1, adresseMail);
            ResultSet rset = stmt.executeQuery();

            if (rset.next()) {
                String dbPasswd = rset.getString(1);
                if (dbPasswd == "") {
                    System.out.println("Cet utilisateur n'existe pas");
                    stmt.close();
                    return false;
                }
                String enteredPasswd = Console.read("Entrez votre MDP : ");
                if (dbPasswd.equals(enteredPasswd)) {
                    System.out.println("Vous êtes connectés");
                    stmt.close();
                    PreparedStatement getId = Database.getDb()
                            .prepareStatement("SELECT idCompte FROM Client WHERE emailClient = ?");
                    getId.setString(1, adresseMail);
                    rset = getId.executeQuery();
                    rset.next();
                    currentIdCompte = rset.getInt(1);
                    if (!recommandations.containsKey(rset.getInt(1))) {
                        recommandations.put(rset.getInt(1), "Cuisine européenne");
                    }
                    System.out.println("Les recommandations pour votre commande sont les suivantes : ");
                    parseListCatRec(recommandations.get(rset.getInt(1)));
                    return true;
                } else {
                    stmt.close();
                    System.out.println("Connexion refusée");
                    return false;
                }
            } else {
                System.out.println("Cet utilisateur n'existe pas");
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
        return false;
    }

    public static void parseAdd() {
        Client client = new Client(Console.read("Entrez l'email du client : "))
                .mdp(Console.read("Entrez le mot de passe du client :"))
                .nomClient(Console.read("Entrez le nom du client : "))
                .prenomClient(Console.read("Entrez le prénom du client : "))
                .adresseClient(Console.read("Entrez l'adresse du client : "));
    }

    public static void parseDel() {
        // TODO lister les clients
        // TODO laisser l'utilisateur en supprimer un
    }

    public static int getLoggedInId() {
        if (currentIdCompte == -1) {
            System.out.println("Vous devez vous connecter pour faire cette action !");
            while (!parseConnexion()) {
                System.out.println("Connexion impossible !");
                System.out.println("Réessayer ? (Y/n)");
                if (Console.read().equals("n")) {
                    System.out.println("Entrez une nouvelle commande.");
                    Console.prompt();
                    return -1;
                }
            }
        }
        return currentIdCompte;
    }

    public Client mdp(String mdp) {
        this.mdp = mdp;
        return this;
    }

    public Client nomClient(String nomClient) {
        this.nomClient = nomClient;
        return this;
    }

    public Client prenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
        return this;
    }

    public Client adresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
        return this;
    }

    @Override
    public String toString() {
        String strIdCompte = String.valueOf(currentIdCompte);

        int sizeEmail = emailClient.length();
        int sizemdp = mdp.length();
        int sizeNom = nomClient.length();
        int sizePrenom = prenomClient.length();
        int sizeAdresse = adresseClient.length();
        int sizeIdCompte = strIdCompte.length();

        StringBuilder retString = new StringBuilder();
        if (firstRowPrinted) {
            retString.append("╔").append(("═").repeat(159)).append("╗").append("\n")
                    .append("║").append((" ").repeat(74)).append("Clients").append((" ").repeat(78)).append("║").append("\n")
                    .append("╠").append(("═").repeat(42)).append("╤").append(("═").repeat(20)).append("╤").append(("═").repeat(17)).append("╤").append(("═").repeat(17)).append("╤").append(("═").repeat(52)).append("╤").append(("═").repeat(6)).append("╣").append("\n")
                    .append(String.format("║ %-40s │", "Email"))
                    .append(String.format(" %-18s ", "mdp"))
                    .append(String.format("│ %-15s │", "Nom"))
                    .append(String.format(" %-15s ", "Prenom"))
                    .append(String.format("│ %-50s │", "Adresse"))
                    .append(String.format(" %-4s ║", "ID"))
                    .append("\n")
                    .append("╠").append(("═").repeat(42)).append("╪")
                    .append(("═").repeat(20))
                    .append("╪").append(("═").repeat(17)).append("╪")
                    .append(("═").repeat(17))
                    .append("╪").append(("═").repeat(52)).append("╪")
                    .append(("═").repeat(6)).append("╣")
                    .append("\n");
            firstRowPrinted = false;
        }
        int i = 0;
        while (i * 40 < sizeEmail
                || i * 18 < sizemdp
                || i * 15 < sizeNom
                || i * 15 < sizePrenom
                || i * 50 < sizeAdresse
                || i * 4 < sizeIdCompte) {
            retString.append(String.format("║ %-40s │", emailClient.substring(min(i * 40, sizeEmail), min((i + 1) * 40, sizeEmail))))
                    .append(String.format(" %-18s ", mdp.substring(min(i * 18, sizemdp), min((i + 1) * 18, sizemdp))))
                    .append(String.format("│ %-15s │", nomClient.substring(min(i * 15, sizeNom), min((i + 1) * 15, sizeNom))))
                    .append(String.format(" %-15s ", prenomClient.substring(min(i * 15, sizePrenom), min((i + 1) * 15, sizePrenom))))
                    .append(String.format("│ %-50s │", adresseClient.substring(min(i * 50, sizeAdresse), min((i + 1) * 50, sizeAdresse))))
                    .append(String.format(" %-4s ║", strIdCompte.substring(min(i * 4, sizeIdCompte), min((i + 1) * 4, sizeIdCompte))))
                    .append("\n");
            i++;
        }

        retString.append("╟").append(("─").repeat(42)).append("┼")
                .append(("─").repeat(20))
                .append("┼").append(("─").repeat(17)).append("┼")
                .append(("─").repeat(17))
                .append("┼").append(("─").repeat(52)).append("┼")
                .append(("─").repeat(6)).append("╢");
        return retString.toString();
    }
}
