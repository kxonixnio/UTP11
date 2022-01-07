package zad1;

import java.sql.*;
import java.text.SimpleDateFormat;

public class Database {

    private String url;
    private TravelData travelData;
    private Connection conn = null;
    private Statement statement;

    public Database(String url, TravelData travelData) {
        this.url = url;
        this.travelData = travelData;
    }

    public void create() {

        try {
            conn = DriverManager.getConnection(url);

            if (conn != null) {
                statement = conn.createStatement();

                String dropTableIfExists = "DROP TABLE IF EXISTS travelData;";
                statement.executeUpdate(dropTableIfExists);

                String createTable = "CREATE TABLE travelData (\n" +
                        "    idTravel int  NOT NULL,\n" +
                        "    countryCode varchar(50)  NOT NULL,\n" +
                        "    countryName varchar(50)  NOT NULL,\n" +
                        "    dateFrom date  NOT NULL,\n" +
                        "    dateTo date  NOT NULL,\n" +
                        "    location varchar(50)  NOT NULL,\n" +
                        "    price float(50)  NOT NULL,\n" +
                        "    currency varchar(3)  NOT NULL,\n" +
                        "    CONSTRAINT travelData_pk PRIMARY KEY  (idTravel)\n" +
                        ");";
                statement.executeUpdate(createTable);

                fillDatabase();
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

    public void fillDatabase() {
        int ID = 1;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        for(Record record : travelData.getDataAsRecords()) {

            String countryCode = record.getCountryCode().toLanguageTag();
            String countryName = record.getCountryName();
            String dateFrom = df.format(record.getDateFrom());
            String dateTo = df.format(record.getDateTo());
            String location = record.getLocation();
            Double price = record.getPrice();
            String currency = record.getCurrency();

            try {
                String insertRecord = "INSERT INTO travelData VALUES " + "('" + ID + "', '" + countryCode + "', '" + countryName + "', '" + dateFrom + "', '" + dateTo + "', '" + location + "', '" + price + "', '" + currency + "');";
                statement.executeUpdate(insertRecord);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            ID += 1;
        }

        try {
            String selectAll = "SELECT * FROM travelData";
            ResultSet rs = statement.executeQuery(selectAll);

            while(rs.next()) {
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));
                System.out.println(rs.getString(3));
                System.out.println(rs.getString(4));
                System.out.println(rs.getString(5));
                System.out.println(rs.getString(6));
                System.out.println(rs.getString(7));
                System.out.println(rs.getString(8));
            }

            /*
            Tu skończyłeś - teraz trzeba zczytać dane z bazy danych i "przekazać" do GUI, które też jest do ogarnięcia
             */

            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void showGui() {
        //Trzeba zczytać i przekazać dane z Database, a nie travelData
        new GUI(travelData);
    }
}


//https://www.youtube.com/watch?v=x8GiogC4SdE
//https://www.sqlshack.com/how-to-install-sql-server-express-edition/
//https://www.codejava.net/java-se/jdbc/connect-to-microsoft-sql-server-via-jdbc#ExampleProgram
//https://sdkp.pjwstk.edu.pl/html/staskshtml/S_INTERDB/S_INTERDB.html
//https://stackoverflow.com/questions/23291346/printing-all-columns-from-select-query-in-java
//https://stackoverflow.com/questions/23291346/printing-all-columns-from-select-query-in-java