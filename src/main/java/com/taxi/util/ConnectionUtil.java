package com.taxi.util;

import com.taxi.exception.DataProcessingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConnectionUtil {
    @Value("${datasource.url}")
    private String url;
    @Value("${datasource.username}")
    private String name;
    @Value("${datasource.password}")
    private String password;

    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            throw new DataProcessingException("Error occurred while trying connect to db", e);
        }
        return connection;
    }
}

