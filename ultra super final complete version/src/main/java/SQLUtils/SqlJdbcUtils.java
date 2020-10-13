package SQLUtils;

import java.sql.*;

class SqlJdbcUtils {
    private static String database;
    private static String hostname;
    private static String sqlInstanceName;
    private static String username;
    private static String password;

    // Kết nối vào SQLServer.
    // (Sử dụng thư viện điều khiển SQLJDBC)
    /** set up SQL connection. */
    public static Connection getSQLServerConnection()
            throws SQLException, ClassNotFoundException {
        hostname = "localhost";
        sqlInstanceName = "MSSQLSERVER";
        database = "Dictionary";
        username = "app";
        password = "1234";

        return getSQLServerConnection(hostname, sqlInstanceName,
                database, username, password);
    }

    // Trường hợp sử dụng SQLServer.
    // Và thư viện SQLJDBC.
    /** set up SQl connection prestige. */
    public static Connection getSQLServerConnection(String hostName,
                                                    String sqlInstanceName, String database, String userName,
                                                    String password) throws ClassNotFoundException, SQLException {
        // Khai báo class Driver cho DB SQLServer
        // Việc này cần thiết với Java 5
        // Java6 tự động tìm kiếm Driver thích hợp.
        // Nếu bạn dùng Java6, thì ko cần dòng này cũng được.
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        // Cấu trúc URL Connection dành cho SQLServer
        // Ví dụ:
        // jdbc:sqlserver://ServerIp:1433/SQLEXPRESS;databaseName=simplehr
        String connectionURL = "jdbc:sqlserver://" + hostName + ":1433"
                + ";instance=" + sqlInstanceName + ";databaseName=" + database;

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
}