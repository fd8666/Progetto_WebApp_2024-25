package org.web24_25.cardswap_backend.database.postgresImplementation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.web24_25.cardswap_backend.database.postgress_implementation.DatabasePostgress;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class DatabasePostgressTest {

    @BeforeAll
    static void setUp() {
        DatabasePostgress.logger.setLevel(Level.OFF);
    }

    @Test
    void getInstance() {
        assertEquals(DatabasePostgress.getInstance(), DatabasePostgress.getInstance());
    }

    @Test
    void getConnection() {
        assertTrue(DatabasePostgress.getInstance().verifyConnectionAndReconnect());
    }
}