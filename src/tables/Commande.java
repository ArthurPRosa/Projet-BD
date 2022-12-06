package tables;

import java.text.ParseException;
import java.time.LocalDateTime;

import demo.Console;

public class Commande {
    private long dateCommande;
    private int heureCommande;
    private int idCompte;
    private String emailRest;
    private TypeCommande typeComm;
    private int prixCommande;
    private statutCommande statut;
    private String adrLivraison = null;
    private String infoLivreur = null;
    private String hLivraison = null;
    private int nbPersonnes = 0;
    private String hArrivee = null;

    public static void parseOrder() {

        Commande commande = new Commande().idCompte(Client.getLoggedInId());

        System.out.println("Quel est le type de commande ?");
        int i = 0;
        for (TypeCommande tc : TypeCommande.values()) {
            System.out.println(i + "> " + tc.toString());
            i++;
        }
        TypeCommande s = Console.readWithParse(null, TypeCommande::parse);

        LocalDateTime now = LocalDateTime.now();
        commande.heureCommande(now.getHour() * 60 + now.getMinute())
                .dateCommande(System.currentTimeMillis())
                .emailRest(Console.read("Entrez l'email du restaurant chez qui passer la commande :"))
                .typeComm(s)
                .statut(statutCommande.ATTENTE);
        if (s == TypeCommande.LIVRAISON) {
            commande.adrLivraison(Console.read("Entrez l'adresse de livraison :"))
                    .infoLivreur(Console.read("Entrez les informations pour le livreur :"))
                    .hLivraison(Console.read("Entrez l'heure de la livraison"));
        } else if (s == TypeCommande.SUR_PLACE) {
            commande.nbPersonnes(
                    Console.readWithParse("Entrez le nombre de places à réserver :", Integer::parseInt))
                    .hArrivee(Console.read("Entrez la date d'arrivée :"));
        }
        System.out.println(commande);
        // TODO requete SQL pour la commande

    }

    public static void parseDel() {
        // TODO afficher les commandes et permettre à l'utilisateur d'en supprimer une
    }

    public Commande dateCommande(long dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public Commande heureCommande(int heureCommande) {
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

    public Commande typeComm(TypeCommande typeComm) {
        this.typeComm = typeComm;
        return this;
    }

    public Commande statut(statutCommande statut) {
        this.statut = statut;
        return this;
    }

    public Commande adrLivraison(String adrLivraison) {
        if (this.typeComm == TypeCommande.LIVRAISON) {
            this.adrLivraison = adrLivraison;
        }
        return this;
    }

    public Commande infoLivreur(String infoLivreur) {
        if (this.typeComm == TypeCommande.LIVRAISON) {
            this.infoLivreur = infoLivreur;
        }
        return this;
    }

    public Commande hLivraison(String hLivraison) {
        if (this.typeComm == TypeCommande.LIVRAISON) {
            this.hLivraison = hLivraison;
        }
        return this;
    }

    public Commande nbPersonnes(int nbPersonnes) {
        if (this.typeComm == TypeCommande.SUR_PLACE) {
            this.nbPersonnes = nbPersonnes;
        }
        return this;
    }

    public Commande hArrivee(String hArrivee) {
        if (this.typeComm == TypeCommande.SUR_PLACE) {
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

    public enum TypeCommande {
        LIVRAISON, A_EMPORTER, SUR_PLACE;

        public static TypeCommande parse(String s) throws ParseException {
            try {
                return TypeCommande.valueOf(s);
            } catch (IllegalArgumentException e) {
                for (TypeCommande j : TypeCommande.values()) {
                    if (s.equalsIgnoreCase(j.name()))
                        return j;
                }
                try {
                    int index = Integer.parseInt(s);
                    if (index >= 0 && index < TypeCommande.values().length)
                        return TypeCommande.values()[index];
                } catch (NumberFormatException e2) {
                }
            }
            System.out.println("Veuillez rentrer un jour valide");
            throw new ParseException(s, 0);
        }
    }

    public enum statutCommande {
        ATTENTE, VALIDEE, LIVRAISON, DISPONIBLE, ANNULEECLIENT, ANNULEEREST;
    }

}
