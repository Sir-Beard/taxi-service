package com.taxi.util;

import com.taxi.exception.DataProcessingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.taxi"})
public class ConnectionUtil {
    private static final String URL = "datasource.url";
    private static final String NAME = "datasource.username";
    private static final String PASSWORD = "datasource.password";

    public Connection getConnection() {
        Connection connection = null;
        Properties properties = null;

        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/main/resources/application.properties"));
            connection = DriverManager.getConnection(properties.getProperty(URL),
                    properties.getProperty(NAME),
                    properties.getProperty(PASSWORD));
        } catch (SQLException | IOException e) {
            throw new DataProcessingException("Error occurred while trying connect to db", e);
        }
        return connection;
    }
}
