package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

import java.util.List;

public interface UsersTable {
    static UsersTable getInstance() {
        return null;
    }
    boolean createUserWithPassword(
            String username,
            String email,
            String password_hash
    );
    boolean createUserWithGoogle(
            String username,
            String email,
            String google_id
    );
    boolean deleteUser(int id);
    UserEntry getUserWithId(int id);
    UserEntry getUserFromUsername(String username);
    List<UserEntry> getUsersRange(int start_id, int limit);
    int getUserCount();
}
