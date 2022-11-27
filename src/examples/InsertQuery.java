package examples;

import java.sql.*;

public class InsertQuery {
    public static void main (String args [])
            throws SQLException
    {
        // Load the Oracle JDBC driver
        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

        // Connect to the database
        // You can put a database name after the @ sign in the connection URL.
        Connection conn =
                DriverManager.getConnection ("jdbc:oracle:oci8:@", "scott", "tiger");

        // Prepare a statement to cleanup the emp table
        Statement stmt = conn.createStatement ();
        try
        {
            stmt.execute ("delete from EMP where EMPNO = 1500");
        }
        catch (SQLException e)
        {
            // Ignore an error here
        }

        try
        {
            stmt.execute ("delete from EMP where EMPNO = 507");
        }
        catch (SQLException e)
        {
            // Ignore an error here too
        }

        // Close the statement
        stmt.close();

        // Prepare to insert new names in the EMP table
        PreparedStatement pstmt =
                conn.prepareStatement ("insert into EMP (EMPNO, ENAME) values (?, ?)");

        // Add LESLIE as employee number 1500
        pstmt.setInt (1, 1500);          // The first ? is for EMPNO
        pstmt.setString (2, "LESLIE");   // The second ? is for ENAME
        // Do the insertion
        pstmt.execute ();

        // Add MARSHA as employee number 507
        pstmt.setInt (1, 507);           // The first ? is for EMPNO
        pstmt.setString (2, "MARSHA");   // The second ? is for ENAME
        // Do the insertion
        pstmt.execute ();

        // Close the statement
        pstmt.close();

        // Close the connecion
        conn.close();

    }
}
