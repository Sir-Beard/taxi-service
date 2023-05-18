package com.taxi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.taxi.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/dbcarservice";
    private static final String NAME = "root";
    private static final String PASSWORD = "password2023";

    private ConnectionUtil() {
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
            throw new DataProcessingException("error in connection method", e);
        }
        return connection;
    }
}
