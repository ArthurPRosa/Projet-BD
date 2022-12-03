package demo;

import java.sql.*;

/**
 * Class handling databases connections, commits, and requests
 */
public class Database {
    /**
     * @return a java.sql.Connection object if the connection with the database succeeded
     */
    public static Connection getDb() {
        return db;
    }

    private static java.sql.Connection db;

    /**
     * Connection to the database, logins hardcoded, requiring VPN connection on top
     */
    public static void connection() {
        try {
            System.out.println("Importation du driver...");
            // DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            DriverManager.registerDriver(new org.mariadb.jdbc.Driver());
            System.out.println("Connexion à la base de données...");
            // db =
            // DriverManager.getConnection("jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1",
            // "pintoroa", "jeej");
            db = DriverManager.getConnection(
                    "jdbc:mariadb://adlors.ddns.net:3306/projetbd?user=test&password=jeej;");
            db.createStatement().execute("SET SQL_MODE='ORACLE';");
            System.out.println("Connection réussie !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Terminate the connection with the database
     */
    public static void closeConnection() {
        try {
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the tables needed by the app
     */
    public static void createTables() {
        createTable("CREATE TABLE Categorie (\n" +
                "nomCategorie VARCHAR(100)," +
                "PRIMARY KEY(nomCategorie)" +
                ")");
        createTable("CREATE TABLE APourMere(" +
                "nomCategorieFille VARCHAR(100), " +
                "nomCategorieMere VARCHAR(100), " +
                "FOREIGN KEY (nomCategorieFille) REFERENCES Categorie (nomCategorie))");
        createTable("CREATE TABLE Restaurant (" +
                "emailRest VARCHAR(320)," +
                "nomRest VARCHAR(100)," +
                "telRest VARCHAR(20)," +
                "adresseRest VARCHAR(100)," +
                "presentation VARCHAR(500)," +
                "capaciteMax INT," +
                "PRIMARY KEY (emailRest)" +
                ")");
        createTable("CREATE TABLE EstCategorieDe (" +
                "emailRest VARCHAR(320)," +
                "nomCategorie VARCHAR(100)," +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest)," +
                "FOREIGN KEY (nomCategorie) REFERENCES Categorie (nomCategorie))");
        createTable("CREATE TABLE Eval (" +
                "dateEval DATE," +
                "heureEval INT," +
                "avis VARCHAR(500)," +
                "note INT," +
                "PRIMARY KEY (dateEval, heureEval, avis, note)" +
                ")");
        createTable("CREATE TABLE Compte (" +
                "idCompte INT," +
                "PRIMARY KEY (idCompte)" +
                ")");
        createTable("CREATE TABLE Commande (" +
                "dateCommande DATE," +
                "heureCommande INT," +
                "idCompte INT," +
                "emailRest VARCHAR(320)," +
                "prixCommande INT," +
                "statut VARCHAR(30) CHECK (statut IN ('attente','validee','disponible','livraison','annuleeClient','annuleeRest')),"
                +
                "PRIMARY KEY (dateCommande)," +
                "FOREIGN KEY (idCompte) REFERENCES Compte (idCompte)," +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest)" +
                ")");
        createTable("CREATE TABLE SurPlace (" +
                "dateCommande DATE," +
                "heureCommande INT," +
                "idCompte INT," +
                "emailRest VARCHAR(320)," +
                "adresseLivraison VARCHAR(100)," +
                "infos VARCHAR(500)," +
                "heureLivraison INT," +
                "FOREIGN KEY (dateCommande) REFERENCES Commande (dateCommande)," +
                "FOREIGN KEY (idCompte) REFERENCES Compte (idCompte)," +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest))");
        createTable("CREATE TABLE Livraison (" +
                "dateCommande DATE," +
                "heureCommande INT," +
                "idCompte INT," +
                "emailRest VARCHAR(320)," +
                "nbPersonne INT," +
                "heureArivee INT," +
                "FOREIGN KEY (dateCommande) REFERENCES Commande (dateCommande)," +
                "FOREIGN KEY (idCompte) REFERENCES Compte (idCompte)," +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest))");
        createTable("CREATE TABLE PossedeEvaluation (" +
                "dateCommande DATE," +
                "heureCommande INT," +
                "idCompte INT," +
                "emailRest VARCHAR(320)," +
                "dateEval DATE," +
                "heureEval INT," +
                "avis VARCHAR(500)," +
                "note INT," +
                "FOREIGN KEY (dateEval, avis, note) REFERENCES Eval (dateEval, avis, note)," +
                "FOREIGN KEY (dateCommande) REFERENCES Commande (dateCommande)," +
                "FOREIGN KEY (idCompte) REFERENCES Compte (idCompte)," +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest))");
        createTable("CREATE TABLE Client (" +
                "emailClient VARCHAR(320)," +
                "mdp VARCHAR(40)," +
                "nomClient VARCHAR(50)," +
                "prenomClient VARCHAR(50)," +
                "adresseClient VARCHAR(100)," +
                "idCompte INT REFERENCES Compte (idCompte)," +
                "PRIMARY KEY (emailClient)" +
                ")");
        createTable("CREATE TABLE Horaire (" +
                "jour VARCHAR(8)," +
                "heureOuverture INT,heureFermeture INT," +
                "PRIMARY KEY (jour, heureOuverture, heureFermeture)" +
                ")");
        createTable("CREATE TABLE PossedeHoraires (" +
                "jour VARCHAR(8)," +
                "heureOuverture INT,heureFermeture INT," +
                "emailRest VARCHAR(320)," +
                "FOREIGN KEY (jour, heureOuverture, heureFermeture) REFERENCES Horaire (jour, heureOuverture, heureFermeture),"
                +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest))");
        createTable("CREATE TABLE Plat (" +
                "emailRest VARCHAR(320)," +
                "nomPlat VARCHAR(100)," +
                "prix INT," +
                "descPlat VARCHAR(500)," +
                "PRIMARY KEY (nomPlat)," +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest))");
        createTable(
                "CREATE TABLE Allergene (" +
                        "nomAllergene VARCHAR(100) PRIMARY KEY" +
                        ")");
        createTable("CREATE TABLE FaitPartieDe (" +
                "dateCommande DATE," +
                "heureCommande INT," +
                "idCompte INT," +
                "emailRest VARCHAR(320)," +
                "nomPlat VARCHAR(100)," +
                "quantiteCommandee INT," +
                "FOREIGN KEY (dateCommande) REFERENCES Commande (dateCommande)," +
                "FOREIGN KEY (idCompte) REFERENCES Compte (idCompte)," +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest)," +
                "FOREIGN KEY (nomPlat) REFERENCES Plat (nomPlat))");

        createTable("CREATE TABLE Contient (" +
                "emailRest VARCHAR(320)," +
                "nomPlat VARCHAR(100)," +
                "nomAllergene VARCHAR(100)," +
                "FOREIGN KEY (emailRest) REFERENCES Restaurant (emailRest)," +
                "FOREIGN KEY (nomPlat) REFERENCES Plat (nomPlat)," +
                "FOREIGN KEY (nomAllergene) REFERENCES Allergene (nomAllergene))");
    }

    /**
     * Executes a query to create a table
     * @param query query to execute
     */
    public static void createTable(String query) {
        try {
            Statement stmt = Database.getDb().createStatement();
            stmt.executeQuery(query);
            stmt.close();
            System.out.println("Created new table " + query.split(" ")[2]);
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    /**
     * Delete the tables used by the app
     */
    public static void deleteTables() {
        dropTable("Contient");
        dropTable("FaitPartieDe");
        dropTable("Allergene");
        dropTable("Plat");
        dropTable("PossedeHoraires");
        dropTable("Horaire");
        dropTable("Client");
        dropTable("PossedeEvaluation");
        dropTable("Livraison");
        dropTable("SurPlace");
        dropTable("Commande");
        dropTable("Compte");
        dropTable("Eval");
        dropTable("EstCategorieDe");
        dropTable("APourMere");
        dropTable("Categorie");
        dropTable("Restaurant");
    }

    /**
     * @param tableName name of the table to drop
     */
    public static void dropTable(String tableName) {
        try {
            Statement stmt = Database.getDb().createStatement();
            stmt.executeQuery("DROP TABLE " + tableName);
            stmt.close();
            System.out.println("Dropped table " + tableName);
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }

    /**
     * Fill the database with some data
     */
    public static void populate() {
        // populate Categorie
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine asiatique')");
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine française')");
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine métropolitaine')");
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine des antilles')");
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine de la Réunion')");
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine savoyarde')");
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine mexicaine')");
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine italienne')");
        executeCommand("INSERT INTO Categorie VALUES ('Cuisine européenne')");
        // populate Restaurant
        executeCommand("INSERT INTO Restaurant VALUES " +
                "('montagne-rouge@outlook.fr'," +
                " 'La Montagne Rouge', " +
                "0169325643, " +
                "'3 rue de la Montagne', " +
                "'Un bon restaurant de la montagne.', " +
                "100)");
        executeCommand("INSERT INTO Restaurant VALUES " +
                "('le-dragon-bleu@sushi.jp'," +
                " 'Le Dragon Bleu', " +
                "0169746643, " +
                "'3 avenue des brebis', " +
                "'Un excellent restaurant asiatique.', " +
                "150)");
        executeCommand("INSERT INTO Restaurant VALUES " +
                "('old-el-paso@wrap.mex'," +
                " 'Tacos et Co', " +
                "0169918243, " +
                "'3 allée des carottes', " +
                "'Un super restaurant mexicain.', " +
                "30)");
        executeCommand("INSERT INTO Restaurant VALUES " +
                "('kssoulet@merguez.fr'," +
                " 'La Petit Cassoulet de Mamie', " +
                "0969325748, " +
                "'3 rue des pépins', " +
                "'Un restaurant du terroir comme on les aime.', " +
                "300)");
        executeCommand("INSERT INTO Restaurant VALUES " +
                "('pizza-campus@orange.fr'," +
                " 'Pizza Campus', " +
                "0169203843, " +
                "'3 rue Giscard Destaing', " +
                "'Le restaurant où aller quand le frigo est vide.', " +
                "200)");
        // populate EstCategorieDe
        executeCommand("INSERT INTO EstCategorieDe VALUES" +
                "('montagne-rouge@outlook.fr'," +
                "'Cuisine savoyarde')");
        executeCommand("INSERT INTO EstCategorieDe VALUES" +
                "('le-dragon-bleu@sushi.jp'," +
                "'Cuisine asiatique')");
        executeCommand("INSERT INTO EstCategorieDe VALUES" +
                "('old-el-paso@wrap.mex'," +
                "'Cuisine mexicaine')");
        executeCommand("INSERT INTO EstCategorieDe VALUES" +
                "('kssoulet@merguez.fr'," +
                "'Cuisine métropolitaine')");
        executeCommand("INSERT INTO EstCategorieDe VALUES" +
                "('pizza-campus@orange.fr'," +
                "'Cuisine italienne')");
        // populate APourMere
        executeCommand("INSERT INTO APourMere VALUES" +
                "('Cuisine savoyarde'," +
                "'Cuisine métropolitaine')");
        executeCommand("INSERT INTO APourMere VALUES" +
                "('Cuisine métropolitaine'," +
                "'Cuisine française')");
        executeCommand("INSERT INTO APourMere VALUES" +
                "('Cuisine des antilles'," +
                "'Cuisine française')");
        executeCommand("INSERT INTO APourMere VALUES" +
                "('Cuisine de la Réunion'," +
                "'Cuisine française')");
        executeCommand("INSERT INTO APourMere VALUES" +
                "('Cuisine française'," +
                "'Cuisine européenne')");
        executeCommand("INSERT INTO APourMere VALUES" +
                "('Cuisine italienne'," +
                "'Cuisine européenne')");
        // populate Horaires
        executeCommand(
                "INSERT INTO Horaire VALUES('Lundi', 690, 900)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Lundi', 1110, 1320)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Mardi', 690, 900)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Mardi', 1110, 1320)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Mercredi', 690, 900)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Mercredi', 1110, 1320)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Jeudi', 690, 900)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Jeudi', 1110, 1320)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Vendredi', 690, 900)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Vendredi', 1110, 1320)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Samedi', 690, 900)");
        executeCommand(
                "INSERT INTO Horaire VALUES('Samedi', 1110, 1320)");
        // populate PossedeHoraires
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mardi', 690, 900, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mardi', 1110, 1320, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mercredi', 690, 900, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mercredi', 1110, 1320, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Vendredi', 690, 900, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Vendredi', 1110, 1320, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'montagne-rouge@outlook.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'montagne-rouge@outlook.fr')");

        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mardi', 690, 900, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mardi', 1110, 1320, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mercredi', 690, 900, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mercredi', 1110, 1320, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Jeudi', 690, 900, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Jeudi', 1110, 1320, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'le-dragon-bleu@sushi.jp')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'le-dragon-bleu@sushi.jp')");

        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mercredi', 690, 900, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mercredi', 1110, 1320, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Jeudi', 690, 900, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Jeudi', 1110, 1320, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Vendredi', 690, 900, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Vendredi', 1110, 1320, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'old-el-paso@wrap.mex')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'old-el-paso@wrap.mex')");

        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mardi', 690, 900, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mardi', 1110, 1320, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Jeudi', 690, 900, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Jeudi', 1110, 1320, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Vendredi', 690, 900, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Vendredi', 1110, 1320, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'kssoulet@merguez.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'kssoulet@merguez.fr')");

        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 690, 900, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Lundi', 1110, 1320, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mardi', 690, 900, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mardi', 1110, 1320, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mercredi', 690, 900, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Mercredi', 1110, 1320, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Jeudi', 690, 900, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Jeudi', 1110, 1320, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Vendredi', 690, 900, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Vendredi', 1110, 1320, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 690, 900, 'pizza-campus@orange.fr')");
        executeCommand(
                "INSERT INTO PossedeHoraires VALUES('Samedi', 1110, 1320, 'pizza-campus@orange.fr')");
        // populate Plat
        executeCommand(
                "INSERT INTO Plat VALUES ('montagne-rouge@outlook.fr', 'Bières moussues', 15, 'Ça réchauffe le ventre !')");
        executeCommand(
                "INSERT INTO Plat VALUES ('montagne-rouge@outlook.fr', 'Fondue géante', 29, 'Ne perdez pas votre crouton...')");
        executeCommand(
                "INSERT INTO Plat VALUES ('le-dragon-bleu@sushi.jp', 'Sushis à volonté', 25, 'Yen a jamais assez.')");
        executeCommand("INSERT INTO Plat VALUES ('le-dragon-bleu@sushi.jp', 'Sashimi', 12, 'C est bon ça !')");
        executeCommand(
                "INSERT INTO Plat VALUES ('old-el-paso@wrap.mex', 'Sauce Old El Paso du Chef', 19, 'No no Jose.')");
        executeCommand(
                "INSERT INTO Plat VALUES ('old-el-paso@wrap.mex', 'Patatas', 13, 'Les bonnes fritas con Patatas.')");
        executeCommand("INSERT INTO Plat VALUES ('old-el-paso@wrap.mex', 'Tacos 8 viandes', 30, 'Ay Caramba !')");
        executeCommand(
                "INSERT INTO Plat VALUES ('kssoulet@merguez.fr', 'Cassoulet', 12, 'Le délicieux cassoulet de Mamie.')");
        executeCommand(
                "INSERT INTO Plat VALUES ('kssoulet@merguez.fr', 'Risotto', 8, 'Vous reprendrez bien un peu de risotto')");
        executeCommand(
                "INSERT INTO Plat VALUES ('pizza-campus@orange.fr', 'Pizza peperonne', 10, 'Un pocco di peperonne')");
        executeCommand(
                "INSERT INTO Plat VALUES ('pizza-campus@orange.fr', 'Pizza Aubergines', 11, 'Qui n aime pas les aubergines')");
        executeCommand(
                "INSERT INTO Plat VALUES ('pizza-campus@orange.fr', 'Pizza 4 saisons', 9, 'En honneur à Vivaldi')");
        // populate Allergene
        executeCommand("INSERT INTO Allergene VALUES ('Lactose')");
        executeCommand("INSERT INTO Allergene VALUES ('Bleu')");
        executeCommand("INSERT INTO Allergene VALUES ('Gluten')");
        // populate Contient
        executeCommand("INSERT INTO Contient VALUES  ('montagne-rouge@outlook.fr', 'Fondue géante', 'Bleu')");
        executeCommand("INSERT INTO Contient VALUES  ('pizza-campus@orange.fr', 'Pizza 4 saisons', 'Bleu')");
        executeCommand("INSERT INTO Contient VALUES  ('pizza-campus@orange.fr', 'Pizza 4 saisons', 'Gluten')");
        executeCommand("INSERT INTO Contient VALUES ('pizza-campus@orange.fr', 'Pizza Aubergines', 'Gluten')");
        executeCommand("INSERT INTO Contient VALUES ('pizza-campus@orange.fr', 'Pizza peperonne', 'Gluten')");
        executeCommand("INSERT INTO Contient VALUES ('old-el-paso@wrap.mex', 'Sauce Old El Paso du Chef', 'Lactose')");
        // populate Compte et Client
        int idCompte = 0;
        executeCommand("INSERT INTO Compte VALUES (" + idCompte + ")");
        executeCommand(
                "INSERT INTO Client VALUES ('michel@mich.fr', 'pastis51', 'Sauzin', 'Michel','3 allée des Pétunias, Saint Martin d Heres',"
                        + idCompte++ + ")");
        executeCommand("INSERT INTO Compte VALUES (" + idCompte + ")");
        executeCommand(
                "INSERT INTO Client VALUES ('sarah@outlook.fr', 'sarahr0127', 'Rossignol', 'Sarah','8 rue de la Mairie, Brest',"
                        + idCompte++ + ")");
        executeCommand("INSERT INTO Compte VALUES (" + idCompte + ")");
        executeCommand(
                "INSERT INTO Client VALUES ('john@connor.terminator', 'T-800', 'Connor', 'John','St Hill Street, Los Angeles',"
                        + idCompte++ + ")");
        executeCommand("INSERT INTO Compte VALUES (" + idCompte + ")");
        executeCommand(
                "INSERT INTO Client VALUES ('bugs@warnerbros.com', 'carrot', 'Bunny', 'Bugs','1 Cartoon street, Hollywood',"
                        + idCompte++ + ")");
        executeCommand("INSERT INTO Compte VALUES (" + idCompte + ")");
        executeCommand(
                "INSERT INTO Client VALUES ('candice.lebret@gmail.com', 'humanity', 'Lebret', 'Candice','7 rue des tulipes, Grenoble',"
                        + idCompte++ + ")");
        executeCommand("INSERT INTO Compte VALUES (" + idCompte + ")");
        executeCommand(
                "INSERT INTO Client VALUES ('friedrich.nietzsche@germany.de', 'ubermensch', 'Nietzsche', 'Friedrich','10 avenue Grand Place',"
                        + idCompte + ")");
    }

    /**
     * @param query SQL query to execute
     */
    public static void executeCommand(String query) {
        try {
            Statement stmt = Database.getDb().createStatement();
            stmt.executeQuery(query);
            stmt.close();
            System.out.println("Successfully executed " + query.split(" ")[0] + " command");
        } catch (SQLException e) {
            System.err.println("SQL request failed");
            e.printStackTrace(System.err);
        }
    }
}
