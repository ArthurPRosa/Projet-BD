package tables;

import demo.Demonstrator;

public class Commande {
    private String dateCommande;
    private String heureCommande;
    private int idCompte;
    private String emailRest;
    private typeCommande typeComm;
    private int prixCommande;
    private statutCommande statut;
    private String adrLivraison = null;
    private String infoLivreur = null;
    private String hLivraison = null;
    private int nbPersonnes = 0;
    private String hArrivee = null;

    public static void parseList() {
        // TODO afficher les commandes depuis la bdd
        // TODO affichage par restaurant
        // TODO affichage par idCompte
        // TODO affichage par statut
    }

    public static void parseAdd() {
        // TODO comment avoir le prix de la commande ?
        System.out.println("Quel est le type de commande ?");
        int i = 0;
        for (typeCommande tc : typeCommande.values()) {
            System.out.println(i +  "> " + tc.toString());
            i++;
        }
        typeCommande s = typeCommande.valueOf(Demonstrator.readConsole());
        Commande commande = new Commande().heureCommande(Demonstrator.readConsole("Entrez l'heure de la commande :"))
                .dateCommande(Demonstrator.readConsole("Entrez la date de la commande : "))
                .idCompte(Integer.parseInt(Demonstrator.readConsole("Entrez l'id du compte qui passe la commande : ")))
                .emailRest(Demonstrator.readConsole("Entrez l'email du restaurant chez qui passer la commande :"))
                .typeComm(s)
                .statut(statutCommande.ATTENTE);
        if (s == typeCommande.LIVRAISON) {
            commande.adrLivraison(Demonstrator.readConsole("Entrez l'adresse de livraison :"))
                    .infoLivreur(Demonstrator.readConsole("Entrez les informations pour le livreur :"))
                    .hLivraison(Demonstrator.readConsole("Entrez l'heure de la livraison"));
        } else if (s == typeCommande.SUR_PLACE) {
            commande.nbPersonnes(Integer.parseInt(Demonstrator.readConsole("Entrez le nombre de places à réserver :")))
                    .hArrivee(Demonstrator.readConsole("Entrez la date d'arrivée :"));
        }
        System.out.println(commande);
    }

    public static void parseDel() {
        // TODO afficher les commandes et permettre à l'utilisateur d'en supprimer une
    }

    public Commande dateCommande(String date) {
        this.dateCommande = dateCommande;
        return this;
    }

    public Commande heureCommande(String date) {
        this.heureCommande = heureCommande;
        return this;
    }

    public Commande idCompte(int idCompte) {
        this.idCompte = idCompte;
        return this;
    }

    public Commande emailRest(String emailRest) {
        this.emailRest = emailRest;
        return this;
    }


    public Commande typeComm(typeCommande typeComm) {
        this.typeComm = typeComm;
        return this;
    }


    public Commande statut(statutCommande statut) {
        this.statut = statut;
        return this;
    }

    public Commande adrLivraison(String adrLivraison) {
        if (this.typeComm == typeCommande.LIVRAISON) {
            this.adrLivraison = adrLivraison;
        }
        return this;
    }

    public Commande infoLivreur(String infoLivreur) {
        if (this.typeComm == typeCommande.LIVRAISON) {
            this.infoLivreur = infoLivreur;
        }
        return this;
    }

    public Commande hLivraison(String hLivraison) {
        if (this.typeComm == typeCommande.LIVRAISON) {
            this.hLivraison = hLivraison;
        }
        return this;
    }

    public Commande nbPersonnes(int nbPersonnes) {
        if (this.typeComm == typeCommande.SUR_PLACE) {
            this.nbPersonnes = nbPersonnes;
        }
        return this;
    }

    public Commande hArrivee(String hArrivee) {
        if (this.typeComm == typeCommande.SUR_PLACE) {
            this.hArrivee = hArrivee;
        }
        return this;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "dateCommande='" + dateCommande + '\'' +
                ", heureCommande='" + heureCommande + '\'' +
                ", idCompte=" + idCompte +
                ", emailRest='" + emailRest + '\'' +
                ", typeComm=" + typeComm +
                ", prixCommande=" + prixCommande +
                ", statut=" + statut +
                ", adrLivraison='" + adrLivraison + '\'' +
                ", infoLivreur='" + infoLivreur + '\'' +
                ", hLivraison='" + hLivraison + '\'' +
                ", nbPersonnes=" + nbPersonnes +
                ", hArrivee='" + hArrivee + '\'' +
                '}';
    }

    public enum typeCommande {
        LIVRAISON, A_EMPORTER, SUR_PLACE
    }

    public enum statutCommande {
        ATTENTE, VALIDEE, LIVRAISON, DISPONIBLE, ANNULEECLIENT, ANNULEEREST
    }

}

