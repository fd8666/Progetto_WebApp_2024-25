package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;

public interface GamesTable {
    static GamesTable getInstance() {
        return null;
    }
    boolean addGame(String name);
    boolean removeGameWithName(String name);
    boolean removeGameWithId(int id);
    GameEntry getGameWithName(String name);
    GameEntry getGameWithId(int id);
}
