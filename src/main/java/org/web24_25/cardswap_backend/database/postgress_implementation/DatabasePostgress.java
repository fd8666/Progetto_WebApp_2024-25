package org.web24_25.cardswap_backend.database.postgress_implementation;

import io.github.cdimascio.dotenv.Dotenv;
import org.web24_25.cardswap_backend.database.structure.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.*;
import java.util.logging.Logger;


public class DatabasePostgress implements AutoCloseable, Database {
    protected final ExecutorService executorService = Executors.newCachedThreadPool();
    private static final Dotenv dotenv = Dotenv.load();
    public static final Logger logger = Logger.getLogger(DatabasePostgress.class.getName()); //TODO: change for global logger (maybe)

    private static DatabasePostgress instance = null;
    public static Connection conn = null;

    private final String dbUrl = dotenv.get("DATASOURCE_URL");
    private final String dbUsername = dotenv.get("DATASOURCE_USERNAME");
    private final String dbPassword = dotenv.get("DATASOURCE_PASSWORD");

    public static DatabasePostgress getInstance() {
        if (instance == null) {
            instance = new DatabasePostgress();
        }
        return instance;
    }

    private DatabasePostgress() {
        verifyConnectionAndReconnect();
    }

    private Connection getConnection() {
        if (conn == null) {
            try {
                logger.info("Attempting to connect to the database...");
                logger.info("Database URL: " + dbUrl);
                logger.info("Database Username: " + dbUsername);
                assert dbUrl != null;
                conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                logger.info("Connection established successfully.");
            } catch (SQLException e) {
                logger.severe("Failed to establish connection: " + e.getMessage());
            }
        }
        return conn;
    }

    public boolean verifyConnectionAndReconnect() {
        if (!isConnected()) {
            return reconnect();
        }
        return true;
    }

    public boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            logger.severe("Failed to check connection status: " + e.getMessage());
            return false;
        }
    }

    public boolean reconnect() {
        try {
            if (isConnected())
                closeConnection();
            assert dbUrl != null;
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
            return true;
        } catch (SQLException e) {
            logger.severe("Failed to reconnect: " + e.getMessage());
        }
        return false;
    }

    public boolean closeConnection() {
        try {
            if (conn != null)
                conn.close();
            conn = null;
            return true;
        } catch (SQLException e) {
            logger.severe("Failed to close connection: " + e.getMessage());
        }
        return false;
    }

    @Override
    public void close() {
        executorService.shutdown();
        closeConnection();
    }
}