package zad1;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private String url;
    private TravelData travelData;

    public Database(String url, TravelData travelData) {
        this.url = url;
        this.travelData = travelData;
    }

    public void create() {

        Connection conn = null;

        try {
//            String dbURL = "jdbc:sqlserver://localhost;integratedSecurity=true;";
            conn = DriverManager.getConnection(url);

            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());


            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    public void showGui() {

    }
}


//https://www.youtube.com/watch?v=x8GiogC4SdE
//https://www.sqlshack.com/how-to-install-sql-server-express-edition/
//https://www.codejava.net/java-se/jdbc/connect-to-microsoft-sql-server-via-jdbc#ExampleProgram
//https://sdkp.pjwstk.edu.pl/html/staskshtml/S_INTERDB/S_INTERDB.html