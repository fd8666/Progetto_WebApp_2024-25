package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

import static org.junit.jupiter.api.Assertions.*;

class UsersTablePostgresTest {

    private DatabasePostgres db = DatabasePostgres.getInstance();

    @Test
    void getInstance() {
        assertEquals(UsersTablePostgres.getInstance(), UsersTablePostgres.getInstance());
    }

    @BeforeEach
    void verifyConnection() {
        assertTrue(db.verifyConnectionAndReconnect());
    }

    @Test
    void createUserWithPassword() {
        boolean ue = UsersTablePostgres.getInstance().createUserWithPassword(
                "testUserPassword",
                "test@ciao",
                "password"
        );
        assertTrue(ue);
    }

    @Test
    void createUserWithGoogle() {
        boolean ue = UsersTablePostgres.getInstance().createUserWithGoogle(
                "testUserGoogle",
                "test@ciao",
                "password"
        );
        assertTrue(ue);
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUserFromId() {
        UserEntry ue = UsersTablePostgres.getInstance().getUserFromId(1);
        assertNotNull(ue);
    }

    @Test
    void getUserFromUsername() {
        createUserWithGoogle();
        UserEntry ue = UsersTablePostgres.getInstance().getUserFromUsername("testUserGoogle");
        assertNotNull(ue);
        assertEquals("testUserGoogle", ue.username());
    }

    @Test
    void getUsersRange() {
    }

    @Test
    void getUserCount() {
    }

    @AfterAll
    static void clean() {
        UserEntry ueg = UsersTablePostgres.getInstance().getUserFromUsername("testUserGoogle");
        while (ueg != null) {
            UsersTablePostgres.getInstance().deleteUser(ueg.id());
            ueg = UsersTablePostgres.getInstance().getUserFromUsername("testUserGoogle");
        }
        UserEntry uep = UsersTablePostgres.getInstance().getUserFromUsername("testUserPassword");
        while (uep != null) {
            UsersTablePostgres.getInstance().deleteUser(uep.id());
            uep = UsersTablePostgres.getInstance().getUserFromUsername("testUserPassword");
        }
        UserEntry ue = UsersTablePostgres.getInstance().getUserFromUsername("testUser");
        while (ue != null) {
            UsersTablePostgres.getInstance().deleteUser(ue.id());
            ue = UsersTablePostgres.getInstance().getUserFromUsername("testUser");
        }
    }
}