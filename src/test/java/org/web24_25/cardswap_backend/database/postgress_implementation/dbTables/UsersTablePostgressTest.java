package org.web24_25.cardswap_backend.database.postgress_implementation.dbTables;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.web24_25.cardswap_backend.database.postgress_implementation.DatabasePostgress;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

import static org.junit.jupiter.api.Assertions.*;

class UsersTablePostgressTest {

    private DatabasePostgress db = DatabasePostgress.getInstance();

    @Test
    void getInstance() {
        assertEquals(UsersTablePostgress.getInstance(), UsersTablePostgress.getInstance());
    }

    @BeforeEach
    void verifyConnection() {
        assertTrue(db.verifyConnectionAndReconnect());
    }

    @Test
    void createUserWithPassword() {
        boolean ue = UsersTablePostgress.getInstance().createUserWithPassword(
                "testUserPassword",
                "test@ciao",
                "password"
        );
        assertTrue(ue);
    }

    @Test
    void createUserWithGoogle() {
        boolean ue = UsersTablePostgress.getInstance().createUserWithGoogle(
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
        UserEntry ue = UsersTablePostgress.getInstance().getUserFromId(1);
        assertNotNull(ue);
    }

    @Test
    void getUserFromUsername() {
        createUserWithGoogle();
        UserEntry ue = UsersTablePostgress.getInstance().getUserFromUsername("testUserGoogle");
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
        UserEntry ueg = UsersTablePostgress.getInstance().getUserFromUsername("testUserGoogle");
        while (ueg != null) {
            UsersTablePostgress.getInstance().deleteUser(ueg.id());
            ueg = UsersTablePostgress.getInstance().getUserFromUsername("testUserGoogle");
        }
        UserEntry uep = UsersTablePostgress.getInstance().getUserFromUsername("testUserPassword");
        while (uep != null) {
            UsersTablePostgress.getInstance().deleteUser(uep.id());
            uep = UsersTablePostgress.getInstance().getUserFromUsername("testUserPassword");
        }
        UserEntry ue = UsersTablePostgress.getInstance().getUserFromUsername("testUser");
        while (ue != null) {
            UsersTablePostgress.getInstance().deleteUser(ue.id());
            ue = UsersTablePostgress.getInstance().getUserFromUsername("testUser");
        }
    }
}