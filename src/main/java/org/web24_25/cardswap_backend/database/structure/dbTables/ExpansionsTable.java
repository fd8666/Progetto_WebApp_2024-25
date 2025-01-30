package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.ExpansionEntry;

public interface ExpansionsTable {
    static ExpansionsTable getInstance() {
        return null;
    }
    boolean addExpansion(String name, int gameId);
    boolean removeExpansionWithName(String name);
    boolean removeExpansionWithId(int id);
    ExpansionEntry getExpansionWithName(String name);
    ExpansionEntry getExpansionWithId(int id);
}
