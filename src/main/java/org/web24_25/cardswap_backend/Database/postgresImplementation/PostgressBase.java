package org.web24_25.cardswap_backend.Database.postgresImplementation;

import lombok.Data;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

@Data
public class PostgressBase {
    private static final Dotenv dotenv = Dotenv.load();
    public static final Logger logger = Logger.getLogger(PostgressBase.class.getName()); //TODO: change for global logger (maybe)

    private static PostgressBase instance = null;
    private static Connection conn = null;

    private String dbUrl = dotenv.get("DATASOURCE_URL");
    private String dbUsername = dotenv.get("DATASOURCE_USERNAME");
    private String dbPassword = dotenv.get("DATASOURCE_PASSWORD");

    public static PostgressBase getInstance() {
        if (instance == null) {
            instance = new PostgressBase();
        }
        return instance;
    }

    public Connection getConnection() {
        if (conn == null) {
            try {
                logger.info("Attempting to connect to the database...");
                logger.info("Database URL: " + dbUrl);
                logger.info("Database Username: " + dbUsername);
                conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                logger.info("Connection established successfully.");
            } catch (SQLException e) {
                logger.severe("Failed to establish connection: " + e.getMessage());
            }
        }
        return conn;
    }
}