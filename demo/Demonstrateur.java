package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Demonstrateur {
    static Connection bd;
    public static void main(String[] args) {
        System.out.println("Bienvenue dans le démonstrateur du projet BD du groupe 5 (ISI1).");
        try{
            System.out.println("Importation du driver...");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("Connection à la base de données...");
            bd = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1", "eyraudh", "eyraudh");
            System.out.println("Connection réussie !");
        }catch(SQLException e) {
            e.printStackTrace();
        }
            System.out.println("ici on fait les dingz");
        try{
            bd.close();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
}