package examples;

import java.sql.*;

public class SimpleTrans {
  
  static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";
  static final String USER = "scott";     // A remplacer pour votre compte, sinon genere une exception
  static final String PASSWD = "tiger";
  
  public SimpleTrans() {
    try {
      // Enregistrement du driver Oracle
      System.out.println("Loading Oracle thin driver...");
      DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
      System.out.println("loaded.");
      
      // Etablissement de la connexion
      System.out.println("Connecting to the database...");
      Connection conn = DriverManager.getConnection(CONN_URL, USER, PASSWD);
      System.out.println("connected.");
      
      // Demarrage de la transaction (implicite dans notre cas)
      conn.setAutoCommit(false);
      conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
      
      // Execution des requetes
      PreparedStatement pstmt = conn.prepareStatement
        ("select * from CineSalles where nbplaces > ? and NomCine like ?");
	
      System.out.println("Salles de plus de 200 places des cinemas commencant par L : ");
      pstmt.setInt(1, 200);
      pstmt.setString(2, "L%");
      
      ResultSet rset = pstmt.executeQuery();
      dumpResult(rset);
      rset.close();
      
      System.out.println("Salles de plus de 300 places des cinemas UGC : ");
      pstmt.setInt(1, 300);
      pstmt.setString(2, "UGC%");
      
      rset = pstmt.executeQuery();
      
      dumpResult(rset);
      rset.close();
      
      // Terminaison de la transaction
      conn.commit();
      
      // Fermetures
      pstmt.close();
      conn.close();
    }
    catch (SQLException e) {
      System.err.println("failed !");
      e.printStackTrace();
    }
  }
    
  void dumpResult(ResultSet r) throws SQLException {
    while (r.next()) {
      System.out.println(
	"Cinema: " + r.getString(1) +
	"- Salle: " + r.getString(2) +
	" - " + r.getInt(3) + " places.");
    }
  }
    
  public static void main(String args[]) {
    new SimpleTrans();
  }
}
