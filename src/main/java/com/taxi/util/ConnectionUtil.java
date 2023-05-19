package com.taxi.util;

import com.taxi.exception.DataProcessingException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"com.taxi"})
public class ConnectionUtil {
    private static final String URL = "spring.datasource.url";
    private static final String NAME = "spring.datasource.username";
    private static final String PASSWORD = "spring.datasource.password";

    private final Environment environment;

    public ConnectionUtil(Environment environment) {
        this.environment = environment;
    }

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
