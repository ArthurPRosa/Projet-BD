package demo.BD.tables;

public class Commande {
    private int idComm;
    private String date;
    private String h;
    private String client;
    private String restau;
    private Restaurant.Type typeComm;
    private String contenu;
    private int pFinal;
    private Statut statut;
    private String adrLivraison = null;
    private String infoLivreur = null;
    private String hLivraison = null;
    private int nbPersonnes = 0;
    private String hArrivee = null;
    private Evaluation evalComm = null;

    public Commande(int idCommande) {
        this.idComm = idCommande;
    }

    public static void parseList() {
    }

    public static void parseAdd() {
    }

    public static void parseDel() {
    }

    public Commande idCommande(int idCommande) {
        this.idComm = idCommande;
        return this;
    }

    public Commande h(String h) {
        this.h = h;
        return this;
    }

    public Commande client(String client) {
        this.client = client;
        return this;
    }

    public Commande restau(String restau) {
        this.restau = restau;
        return this;
    }

    public Commande typeComm(Restaurant.Type typeComm) {
        this.typeComm = typeComm;
        return this;
    }

    public Commande contenu(String contenu) {
        this.contenu = contenu;
        return this;
    }

    public Commande pFinal(int pFinal) {
        this.pFinal = pFinal;
        return this;
    }

    public Commande statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public Commande adrLivraison(String adrLivraison) {
        if (this.typeComm == Restaurant.Type.LIVRAISON) {
            this.adrLivraison = adrLivraison;
        }
        return this;
    }

    public Commande infoLivreur(String infoLivreur) {
        if (this.typeComm == Restaurant.Type.LIVRAISON) {
            this.infoLivreur = infoLivreur;
        }
        return this;
    }

    public Commande hLivraison(String hLivraison) {
        if (this.typeComm == Restaurant.Type.LIVRAISON) {
            this.hLivraison = hLivraison;
        }
        return this;
    }

    public Commande nbPersonnes(int nbPersonnes) {
        if (this.typeComm == Restaurant.Type.SUR_PLACE) {
            this.nbPersonnes = nbPersonnes;
        }
        return this;
    }

    public Commande hArrivee(String hArrivee) {
        if (this.typeComm == Restaurant.Type.SUR_PLACE) {
            this.hArrivee = hArrivee;
        }
        return this;
    }

    public Commande evalComm(Evaluation evalComm) {
        this.evalComm = evalComm;
        return this;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "idComm=" + idComm +
                ", date='" + date + '\'' +
                ", h='" + h + '\'' +
                ", client='" + client + '\'' +
                ", restau='" + restau + '\'' +
                ", typeComm=" + typeComm +
                ", contenu='" + contenu + '\'' +
                ", pFinal=" + pFinal +
                ", statut=" + statut +
                ", adrLivraison='" + adrLivraison + '\'' +
                ", infoLivreur='" + infoLivreur + '\'' +
                ", hLivraison='" + hLivraison + '\'' +
                ", nbPersonnes=" + nbPersonnes +
                ", hArrivee='" + hArrivee + '\'' +
                ", evalComm=" + evalComm +
                '}';
    }
}

