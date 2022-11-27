package tables;

import demo.Console;

public class Client {
    private String emailClient;
    private String mdp;
    private String nomClient;
    private String prenomClient;
    private String adresseClient;
    private static int idCompte = 0;

    @Override
    public String toString() {
        return "Client{" +
                "emailClient='" + emailClient + '\'' +
                ", mdp='" + mdp + '\'' +
                ", nomClient='" + nomClient + '\'' +
                ", prenomClient='" + prenomClient + '\'' +
                ", adresseClient='" + adresseClient + '\'' +
                '}';
    }

    public Client(String emailClient) {
        this.emailClient = emailClient;
    }

    public static void parseList() {
        // TODO lister les clients depuis la bdd
    }

    public static void parseAdd() {
        Client client = new Client(Console.read("Entrez l'email du client : "))
                .mdp(Console.read("Entrez le mot de passe du client :"))
                .nomClient(Console.read("Entrez le nom du client : "))
                .prenomClient(Console.read("Entrez le prénom du client : "))
                .adresseClient(Console.read("Entrez l'adresse du client : "));
        idCompte++;
    }

    public static void parseDel() {
        // TODO lister les clients
        // TODO laisser l'utilisateur en supprimer un
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
