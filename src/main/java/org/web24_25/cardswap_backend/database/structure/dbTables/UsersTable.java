package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

import java.util.List;

public interface UsersTable {
    static UsersTable getInstance() {
        return null;
    }
    UserEntry createUser(String username, String password_hash);
    boolean deleteUser(int id);
    UserEntry getUser(int id);
    List<UserEntry> getAllUsers();
}
