package demo;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class handling databases connections, commits, and requests
 */
public class Database {
    private static java.sql.Connection db;

    public static void connection() {
        try {
            System.out.println("Importation du driver...");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("Connexion à la base de données...");
            db = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1", "eyraudh", "eyraudh");
            System.out.println("Connection réussie !");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            db.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
