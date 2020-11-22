import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;
import java.util.ArrayList;

/**
 * This program demonstrates how to make database connection with Oracle
 
 *
 */
public class JdbcHelper {
    Connection conn1;
    Statement stmt;

    public JdbcHelper() {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String dbURL1 = "jdbc:oracle:thin:user/pass@oracle.scs.ryerson.ca:1521:orcl";  // that is school Oracle database and you can only use it in the labs								
			conn1 = DriverManager.getConnection(dbURL1);
            if (conn1 != null) {
                System.out.println("Connected with connection #1");
            }
		
			stmt = conn1.createStatement();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn1 != null && !conn1.isClosed()) {
                conn1.close();
            }
 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String query(String q) {
        StringBuilder sb = new StringBuilder();
        try {
            ResultSet rs = stmt.executeQuery(q);
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
			while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    String s = rs.getString(i);
                    sb.append(s + " ");
                }
                sb.append("\n");
			}			
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return sb.toString();
    }

    public ArrayList getColumnNames(String table) {
        ArrayList names = new ArrayList();
        try {
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            for (int i = 1; i <= cols; i++) {
                names.add(md.getColumnName(i));
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
        }
        return names;
    }
}
