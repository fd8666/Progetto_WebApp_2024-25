package org.web24_25.cardswap_backend.Database.postgresImplementation;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class PostgressBaseTest {

    @BeforeAll
    static void setUp() {
        PostgressBase.logger.setLevel(Level.OFF);
    }

    @Test
    void getInstance() {
        assertEquals(PostgressBase.getInstance(), PostgressBase.getInstance());
    }

    @Test
    void getConnection() {
        assertNotNull(PostgressBase.getInstance().getConnection());
    }
}