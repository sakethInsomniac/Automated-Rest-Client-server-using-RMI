package Utility;

import java.sql.*;


public class DBConnect {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // db4net database connection
//    public static final String DB_URL = "jdbc:mysql://db4free.net/advjavarmi?useSSL=false";
//    public static final String USER = "swinjava";
//    public static final String PASS = "T8hs8QCP9pqcSPQ";

    // local db connection
    public static final String DB_URL = "jdbc:mysql://localhost:3306/advjavarmi";
    public static final String USER = "root";
    public static final String PASS = "";


    private static Connection c;

    public void dbconnectCheck() {
        try {
            Class.forName(JDBC_DRIVER);
            Connection con = DriverManager.getConnection(
                    DB_URL, USER, PASS);
//here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from customerDetails");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getInt(3) + "  " + rs.getString(4));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public int dbconnectExecute(String queryCommand) {
        int numero = 0;
        try {

            Class.forName(JDBC_DRIVER);
            Connection con = DriverManager.getConnection(
                    DB_URL, USER, PASS);
            PreparedStatement ps = con.prepareStatement(queryCommand, Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                numero = rs.getInt(1);
            }
            System.out.println(numero);
            con.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        return numero;
    }

    /**
     * for establishing connection to the database
     *
     * @param dbName database name to connect to
     * @return database connection
     */
    public Connection getConnection(String dbName) throws ClassNotFoundException, SQLException {
        if (c == null || c.isClosed()) {
            Class.forName(JDBC_DRIVER);
            c = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return c;
    }

    /**
     * for establishing connection to the database
     *
     * @return database connection
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if (c == null || c.isClosed()) {
            Class.forName(JDBC_DRIVER);
            c = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        return c;
    }

    /**
     * Send data TO Database
     *
     * @param sql    SQL query
     * @throws Exception @{@link Exception} if any exception occurs
     */
    public void setData(String sql) throws Exception {
        getConnection(DB_URL).createStatement().executeUpdate(sql);
    }

    /**
     * Get Data From Database
     *
     * @param sql    SQL query
     * @return Resultset containing the data
     * @throws Exception @{@link Exception} if any exception occurs
     */
    public ResultSet getData(String sql) throws Exception {
        return getConnection(DB_URL).createStatement().executeQuery(sql);
    }

    /**
     * closes provided database connection
     *
     * @param dbName database name to which connection is to be closed
     * @throws SQLException           @{@link SQLException}
     * @throws ClassNotFoundException @{@link ClassNotFoundException} occurs when given class is not available
     */
    public void close(String dbName) throws SQLException, ClassNotFoundException {
        if (!getConnection(dbName).isClosed()) {
            getConnection(dbName).close();
        }
    }
}
