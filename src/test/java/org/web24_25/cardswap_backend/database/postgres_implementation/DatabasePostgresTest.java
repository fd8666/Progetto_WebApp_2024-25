package org.web24_25.cardswap_backend.database.postgres_implementation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class DatabasePostgresTest {

    @BeforeAll
    static void setUp() {
        DatabasePostgres.logger.setLevel(Level.OFF);
    }

    @Test
    void getInstance() {
        assertEquals(DatabasePostgres.getInstance(), DatabasePostgres.getInstance());
    }

    @Test
    void getConnection() {
        assertTrue(DatabasePostgres.getInstance().verifyConnectionAndReconnect());
    }
}