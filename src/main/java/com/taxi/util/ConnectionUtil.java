package com.taxi.util;

import com.taxi.exception.DataProcessingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConnectionUtil {
    @Value("datasource.url")
    private String url;
    @Value("datasource.username")
    private String name;
    @Value("datasource.password")
    private String password;

    public Connection getConnection() {
        Connection connection = null;
        Properties properties = null;

        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/application.properties"));
            connection = DriverManager.getConnection(properties.getProperty(url),
                    properties.getProperty(name),
                    properties.getProperty(password));
        } catch (SQLException | IOException e) {
            throw new DataProcessingException("Error occurred while trying connect to db", e);
        }
        return connection;
    }
}

