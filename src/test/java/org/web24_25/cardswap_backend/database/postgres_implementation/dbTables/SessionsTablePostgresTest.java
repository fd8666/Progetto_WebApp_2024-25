package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.junit.jupiter.api.Test;
import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;

import static org.junit.jupiter.api.Assertions.*;
class SessionsTablePostgresTest {
    private DatabasePostgres db = DatabasePostgres.getInstance();

    @Test
    void getInstance() {
        assertEquals(SessionsTablePostgres.getInstance(), SessionsTablePostgres.getInstance());
    }

    @Test
    void createSession() {
    }

    @Test
    void getSessionById() {
    }

    @Test
    void getSessionByCookie() {
    }

    @Test
    void getValidUsersSessions() {
    }

    @Test
    void getValidUserSessions() {
    }
}