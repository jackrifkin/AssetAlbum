package com.dishianerifkinj.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/assetAlbum";

    private static final String dbUsername = "root";

    private static final String dbPassword = "bumVe5-nokguf-qozkym";

    public static Connection getConnection() throws SQLException {
        Connection conn;
        Properties connectionProps = new Properties();
        connectionProps.put("user", dbUsername);
        connectionProps.put("password", dbPassword);

        conn = DriverManager.getConnection(dbUrl, connectionProps);

        return conn;
    }
}
