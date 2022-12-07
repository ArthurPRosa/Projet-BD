package tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import demo.Console;
import demo.Database;

public class Commande {
    private Date dateCommande;
    private int idCompte;
    private String emailRest;
    private TypeCommande typeComm;
    private int prixCommande;
    private statutCommande statut;
    private String adrLivraison = null;
    private String infoLivreur = null;
    private int heureLivraison;
    private int nbPersonnes;
    private int hArrivee;
    private LinkedList<Items> items = new LinkedList<>();

    public static void parseOrder() {

        Commande commande = new Commande().idCompte(Client.getLoggedInId());

        System.out.println("Quel est le type de commande ?");
        int i = 0;
        for (TypeCommande tc : TypeCommande.values()) {
            System.out.println(i + "> " + tc.toString());
            i++;
        }
        TypeCommande s = Console.readWithParse(null, TypeCommande::parse);

        Date now = new Date();
        String emailRest = Console.read("Entrez l'email du restaurant chez qui passer la commande :");
        commande.dateCommande(now)
                .emailRest(emailRest)
                .typeComm(s)
                .statut(statutCommande.ATTENTE);

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");

        if (s == TypeCommande.SUR_PLACE) {
            commande.nbPersonnes(
                    Console.readWithParse("Entrez le nombre de places à réserver :", Integer::parseInt));
            try {
                PreparedStatement getCapacity = Database.getDb()
                        .prepareStatement("SELECT capaciteMax FROM Restaurant WHERE emailRest = ?");
                getCapacity.setString(1, emailRest);
                ResultSet res = getCapacity.executeQuery();
                int capaciteMax = 0;
                if (!(res.next() && (capaciteMax = res.getInt("capaciteMax")) >= commande.nbPersonnes)) {
                    System.out.println("Impossible de passer votre commande, le restaurant n'a une capacité que de "
                            + capaciteMax + " personnes !");
                    if (!Console.readWithParse("Voulez vous plutôt prendre à emporter ?", Console::customParseBool))
                        return;
                    commande.typeComm(TypeCommande.A_EMPORTER);
                }
                commande.hArrivee(Console.readWithParse(
                        "Entrez l'heure d'arrivée : (format : " + format.toPattern() + ") ",
                        (entry) -> {
                            Calendar date = GregorianCalendar.getInstance();
                            date.setTime(format.parse(entry));
                            return date.get(Calendar.HOUR) * 60 + date.get(Calendar.MINUTE);
                        }));

            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

        }
        if (s == TypeCommande.LIVRAISON) {
            commande.adrLivraison(Console.read("Entrez l'adresse de livraison :"))
                    .infoLivreur(Console.read("Entrez les informations pour le livreur :"))
                    .heureLivraison(Console.readWithParse(
                            "Entrez la date de livraison (format : " + format.toPattern() + ") ",
                            (entry) -> {
                                Calendar date = GregorianCalendar.getInstance();
                                date.setTime(format.parse(entry));
                                return date.get(Calendar.HOUR) * 60 + date.get(Calendar.MINUTE);
                            }));
        }

        boolean adding = true;
        while (adding) {
            Items item = new Items();
            item.parseItem();
            commande.addItem(item);
            adding = Console.readWithParse("Voulez vous ajouter des plats à la commande ? (y/N)",
                    Console::customParseBool);
        }

        // System.out.println(commande);
        commande.addOrder();

    }

    public void addOrder() {
        try {
            Database.getDb().setAutoCommit(false);
            // Database.getDb().prepareStatement("SET CONSTRAINT FOREIGN_KEY
            // DEFERRED").execute();
            Database.getDb().prepareStatement("SET foreign_key_checks=0").execute();
            PreparedStatement insertFaitPartieDe = Database.getDb()
                    .prepareStatement("INSERT INTO FaitPartieDe VALUES (?,?,?,?,?)");
            PreparedStatement getPrice = Database.getDb()
                    .prepareStatement("SELECT prix FROM Plat WHERE emailRest=? AND nomPlat = ?");
            int prix = 0;
            for (Items item : items) {
                insertFaitPartieDe.setTimestamp(1,
                        Timestamp.valueOf(dateCommande.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
                insertFaitPartieDe.setInt(2, idCompte);
                insertFaitPartieDe.setString(3, emailRest);
                insertFaitPartieDe.setString(4, item.getNomPlat());
                insertFaitPartieDe.setInt(5, item.getQte());
                insertFaitPartieDe.executeUpdate();

                getPrice.setString(1, emailRest);
                getPrice.setString(2, item.getNomPlat());
                ResultSet res = getPrice.executeQuery();
                if (res.next()) {
                    prix += res.getInt(1) * item.getQte();
                } else {
                    Database.getDb().rollback();
                    System.err.println(
                            "Aucun prix n'a été trouvé pour " + item.nomPlat + " dans le restaurant " + emailRest);
                    return;
                }

            }

            PreparedStatement insertCommande = Database.getDb()
                    .prepareStatement("INSERT INTO Commande VALUES (?,?,?,?,?)");
            insertCommande.setTimestamp(1,
                    Timestamp.valueOf(dateCommande.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
            insertCommande.setInt(2, idCompte);
            insertCommande.setString(3, emailRest);
            insertCommande.setInt(4, prix);
            insertCommande.setString(5, statut.toString());
            insertCommande.executeUpdate();

            switch (typeComm) {
                case LIVRAISON -> {
                    PreparedStatement livraison = Database.getDb()
                            .prepareStatement("INSERT INTO Livraison VALUES (?,?,?,?,?,?)");
                    livraison.setTimestamp(1,
                            Timestamp.valueOf(
                                    dateCommande.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
                    livraison.setInt(2, idCompte);
                    livraison.setString(3, emailRest);
                    livraison.setString(4, adrLivraison);
                    livraison.setString(5, infoLivreur);
                    livraison.setInt(6, heureLivraison);
                    livraison.executeUpdate();
                }
                case SUR_PLACE -> {
                    PreparedStatement surPlace = Database.getDb()
                            .prepareStatement("INSERT INTO SurPlace VALUES (?,?,?,?,?)");
                    surPlace.setTimestamp(1,
                            Timestamp.valueOf(
                                    dateCommande.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
                    surPlace.setInt(2, idCompte);
                    surPlace.setString(3, emailRest);
                    surPlace.setInt(4, nbPersonnes);
                    surPlace.setInt(5, hArrivee);
                    surPlace.executeUpdate();
                }
                case A_EMPORTER -> {
                }
            }

            Database.getDb().prepareStatement("SET foreign_key_checks=1").execute();
            Database.getDb().commit();
            Database.getDb().setAutoCommit(true);
        } catch (SQLException e) {

        }
    }

    private void addItem(Items item) {
        this.items.addLast(item);
    }

    public static void parseDel() {
        // TODO afficher les commandes et permettre à l'utilisateur d'en supprimer une
    }

    public Commande dateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
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

    public Commande heureLivraison(int heureLivraison) {
        if (this.typeComm == TypeCommande.LIVRAISON) {
            this.heureLivraison = heureLivraison;
        }
        return this;
    }

    public Commande nbPersonnes(int nbPersonnes) {
        if (this.typeComm == TypeCommande.SUR_PLACE) {
            this.nbPersonnes = nbPersonnes;
        }
        return this;
    }

    public Commande hArrivee(int hArrivee) {
        if (this.typeComm == TypeCommande.SUR_PLACE) {
            this.hArrivee = hArrivee;
        }
        return this;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "dateCommande='" + dateCommande + '\'' +
                ", idCompte=" + idCompte +
                ", emailRest='" + emailRest + '\'' +
                ", typeComm=" + typeComm +
                ", prixCommande=" + prixCommande +
                ", statut=" + statut +
                ", adrLivraison='" + adrLivraison + '\'' +
                ", infoLivreur='" + infoLivreur + '\'' +
                ", hLivraison='" + heureLivraison + '\'' +
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

    public static class Items {
        private String nomPlat;
        private int qte;

        public void parseItem() {
            this.nomPlat = Console.readWithParse("Quel est le nom du plat ?", Items::checkExistingPlat);
            this.qte = Console.readWithParse("Combien en voulez vous ?", Integer::parseInt);
        }

        private static String checkExistingPlat(String s) throws ParseException {
            try {
                PreparedStatement stmt = Database.getDb().prepareStatement("SELECT 1 FROM Plat WHERE nomPlat = ?");
                stmt.setString(1, s);
                ResultSet rslt = stmt.executeQuery();
                if (!rslt.next()) {
                    System.err.println("Database has no entry " + s);
                    throw new ParseException(s, 0);
                }
                return s;
            } catch (SQLException e) {
                e.printStackTrace();
                throw new ParseException(s, 0);
            }
        }

        public String getNomPlat() {
            return nomPlat;
        }

        public int getQte() {
            return qte;
        }

    }

    public enum statutCommande {
        ATTENTE, VALIDEE, LIVRAISON, DISPONIBLE, ANNULEECLIENT, ANNULEEREST;
    }

}
