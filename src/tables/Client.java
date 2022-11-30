package tables;

import demo.Console;
import demo.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
    private String emailClient;
    private String mdp;
    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private static int currentIdCompte = -1;

    @Override
    public String toString() {
        return "Client{" +
                "emailClient='" + emailClient + '\'' +
                ", mdp='" + mdp + '\'' +
                ", nomClient='" + nomClient + '\'' +
                ", prenomClient='" + prenomClient + '\'' +
                ", adresseClient='" + adresseClient + '\'' +
                ", idCompte='" + currentIdCompte + '\'' +
                '}';
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
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    /*  */
    public static void parseConnexion() {
        String adresseMail = Console.read("Entrez votre adresse mail : ");

        /* ------- */
        try {
            PreparedStatement stmt = Database.getDb().prepareStatement("SELECT mdp FROM CLIENT WHERE emailClient = ?");
            stmt.setString(1, adresseMail);
            ResultSet rset = stmt.executeQuery();

            if (rset.next()) {
                String password = rset.getString(1);
                if (password == "") {
                    System.out.println("Cet utilisateur n'existe pas");
                    stmt.close();
                    return;
                }
                String motDePasse = Console.read("Entrez votre MDP : ");
                if (password.equals(motDePasse)) {
                    System.out.println("Vous êtes connectés");
                    stmt.close();
                    PreparedStatement getId = Database.getDb()
                            .prepareStatement("SELECT idCompte FROM Client WHERE emailClient = ?");
                    getId.setString(1, adresseMail);
                    rset = stmt.executeQuery();
                    rset.next();
                    currentIdCompte = rset.getInt(1);
                    return;
                }
            }
            stmt.close();
            System.out.println("Connexion refusée");
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
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

}
