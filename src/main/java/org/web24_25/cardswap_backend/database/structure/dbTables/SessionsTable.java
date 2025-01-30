package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.SessionEntry;

import java.util.List;

public interface SessionsTable {
    static SessionsTable getInstance() {
        return null;
    }
    boolean createSession(int user_id, String cookie);
    SessionEntry getSessionById(int id);
    SessionEntry getSessionByCookie(String cookie);
    List<SessionEntry> getValidUsersSessions(int start_id, int limit);
    List<SessionEntry> getValidUserSessions(int user_id);
}
