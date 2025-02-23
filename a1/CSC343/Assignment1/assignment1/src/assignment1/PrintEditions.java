package assignment1;
import java.io.*;
import java.sql.*;
import java.util.Properties;

public class PrintEditions {
	public static final String PROGRAM_NAME = "PrintEditions";
	public static void print (Connection conn) throws SQLException {
		Statement stmt = null;
		String query = "select * from edition";
		
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int cols = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i=0; i < cols; i++)
					System.out.println(rsmd.getColumnLabel(i+1) + ": " + rs.getObject(i+1));
				System.out.print("\n");
			}
		} catch (SQLException e ) {
	    	SQLError.show(e);
		       // JDBCTutorialUtilities.printSQLException(e);
		    } finally {
		        if (stmt != null) { stmt.close(); }
		    }
		}
}
	
