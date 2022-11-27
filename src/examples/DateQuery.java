package examples;

import java.sql.*;
import java.util.Calendar;

public class DateQuery {

    static final String CONN_URL = "jdbc:oracle:thin:@oracle1.ensimag.fr:1521:oracle1";

    static final String USER = "scott";
    static final String PASSWD = "tiger";

    static final String PRE_STMT1 =
        "select min(hiredate),max(hiredate) from emp";

    static final String PRE_STMT2 =
        "select ename,hiredate from emp where to_char(hiredate,'DD-MM-YY') = ?";

    static final String PRE_STMT3 =
        "select ename,hiredate from emp where hiredate > ?";

    public DateQuery() {
        try {

            // Enregistrement du driver Oracle

            System.out.print("Loading Oracle driver... "); 
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            System.out.println("loaded");

            // Etablissement de la connection

            System.out.print("Connecting to the database... "); 
            Connection conn = DriverManager.getConnection(CONN_URL, USER, PASSWD);
            System.out.println("connected");

         /* Recherche de la date d'embauche la plus ancienne et
          * de la plus r�cente dans la table EMP
          */

            // Creation de la requete

            PreparedStatement stmt = conn.prepareStatement(PRE_STMT1);

            // Execution de la requete

            ResultSet rset = stmt.executeQuery();

            // Affichage du resultat

            System.out.println("Results (date d'embauche la plus ancienne et la plus recente :");

            Date min, max;

            if (rset.next()) {
                min = rset.getDate(1);
                max = rset.getDate(2);
                System.out.println("min: " + min);
                System.out.println("max: " + max);
            } else {
                System.out.println("No element retrieved");
            }

            System.out.println();

            // Fermeture

            rset.close();
            stmt.close();

         /* Recherche des employ�s embauch�s le 3 d�cembre 1981
          */

            // Creation de la requete

            stmt = conn.prepareStatement(PRE_STMT2);
            stmt.setString(1, "03-12-81");

            // Execution de la requete

            rset = stmt.executeQuery();

            // Affichage du resultat

            System.out.println("Results:");
            dumpResultSet(rset);
            System.out.println("");

            // Fermeture

            rset.close();
            stmt.close();

          /* Recherche des employ�s embauch�s apr�s le 1er janvier 1982
           *
           * Cr�ation de la date du 1er janvier 1982.
           * On peut directement utiliser new java.sql.Date(82, 0, 1)
           * cependant Calendar permet de r�aliser des calculs sur les
           * dates si n�cessaire.
           * Attention aux conflits entre java.util.Date et
           * java.sql.Date.
           */

            //Creation d'un objet Calendar 
            
            Calendar cal = new java.util.GregorianCalendar(1982, 0, 1);
            
            //Creation d'un objet Date (de java.sql.Date) equivalent
            //� notre objet Calendar (on donne a l'objet Date d le meme
            //nombre de milisecondes depuis "January 1, 1970, 00:00:00 GMT")
            
            //remarque 1 : cal.getTime() revoie un objet Date (java.util)
            //il faut faire cal.getTime().getTime() pour obtenir un long
            //correspondand au nombre de milisecondes depuis 
            //"January 1, 1970, 00:00:00 GMT"
            
            //remarque 2 : il est possible de r�aliser l'op�ration
            //inverse, c'est � dire convertir un objet java.sql.Date
            //en java.util.Calendar
            
            //remarque 3 : pour savoir les op�rations disponnibles
            //sur les objets Calendar et Date (java.sql), voir les
            //description de classes et de m�thodes correspondantes
            //dans l'API java : 
            //http://www.javasoft.com/products/jdk/1.1/docs/api/java.util.Calendar.html
            //http://www.javasoft.com/products/jdk/1.1/docs/api/java.util.GregorianCalendar.html
            //http://www.javasoft.com/products/jdk/1.1/docs/api/java.sql.Date.html
            
            Date d = new Date(cal.getTime().getTime());

            // Creation de la requete

            stmt = conn.prepareStatement(PRE_STMT3);
            stmt.setDate(1, d);

            // Execution de la requete

            rset = stmt.executeQuery();

            // Affichage du resultat

            System.out.println("Results:");
            dumpResultSet(rset);
            System.out.println("");

            // Fermeture

            rset.close();
            stmt.close();

            conn.close();

        } catch (SQLException e) {
            System.err.println("failed");
            e.printStackTrace(System.err);
        }
    }

    private void dumpResultSet(ResultSet rset) throws SQLException {

        ResultSetMetaData rsetmd = rset.getMetaData();
        int i = rsetmd.getColumnCount();

        while (rset.next()) {
            for (int j = 1; j <= i; j++) {
                System.out.print(rset.getString(j) + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String args[]) {
        new DateQuery();
    }
}
