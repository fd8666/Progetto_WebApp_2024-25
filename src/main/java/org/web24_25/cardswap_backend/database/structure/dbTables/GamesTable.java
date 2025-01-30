package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.ExpansionEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;

import java.util.List;

public interface GamesTable {
    static GamesTable getInstance() {
        return null;
    }
    boolean addGame(String name);
    boolean removeGameWithName(String name);
    boolean removeGameWithId(int id);
    GameEntry getGameWithName(String name);
    GameEntry getGameWithId(int id);
    List<ExpansionEntry> getExpansionsForGame(int id);
    List<CardEntry> getCardsForGame(int id);
}
