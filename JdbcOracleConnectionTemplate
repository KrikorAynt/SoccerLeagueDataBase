import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
 
/**
 * This program demonstrates how to make database connection with Oracle
 
 *
 */
public class JdbcOracleConnectionTemplate {
 
    public static void main(String[] args) {
 
        Connection conn1 = null;

        try {
            // registers Oracle JDBC driver - though this is no longer required
            // since JDBC 4.0, but added here for backward compatibility
            Class.forName("oracle.jdbc.OracleDriver");
 
           
            //String dbURL1 = "jdbc:oracle:thin:aboukhal/psswrd@oracle.scs.ryerson.ca:1521:orcl";  // that is school Oracle database and you can only use it in the labs															
         	
            String dbURL1 = "jdbc:oracle:thin:system/requests@localhost:1521:xe";
			/* This XE or local database that you installed on your laptop. 1521 is the default port for database, change according to what you used during installation. 
			xe is the sid, change according to what you setup during installation. */
			
			conn1 = DriverManager.getConnection(dbURL1);
            if (conn1 != null) {
                System.out.println("Connected with connection #1");
            }
 
 		
			
		
            //In your database, you should have a table created already with at least 1 row of data. In this select query example, table testjdbc was already created with at least 2 rows of data with columns NAME and NUM.
			//When you enter your data into the table, please make sure to commit your insertions to ensure your table has the correct data. So the commands that you need to type in Sqldeveloper are
			// CREATE TABLE TESTJDBC (NAME varchar(8), NUM NUMBER);
            // INSERT INTO TESTJDBC VALUES ('ALIS', 67);
            // INSERT INTO TESTJDBC VALUES ('BOB', 345);
            // COMMIT;
			
			String query = "select STADIUM_ID from STADIUM";
							
			try (Statement stmt = conn1.createStatement()) {

			ResultSet rs = stmt.executeQuery(query);
				
			System.out.println(rs);
			

			//If everything was entered correctly, this loop should print each row of data in your TESTJDBC table.
			// And you should see the results as follows:
			// Connected with connection #1
			// ALIS, 67
			// BOB, 345
			
			while (rs.next()) {
				int num = rs.getInt("STADIUM_ID");
				//String name = rs.getString("Member_ID");
				System.out.println(num);
				//System.out.println(name + ", " + num);
			}			
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}


 
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn1 != null && !conn1.isClosed()) {
                    conn1.close();
                }
     
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
			

    }
}
