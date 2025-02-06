package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.ExpansionEntry;
import org.web24_25.cardswap_backend.requests.AddExpansion;

import java.util.List;

public interface ExpansionsTable {
    static ExpansionsTable getInstance() {
        return null;
    }
    boolean addExpansion(String name, int gameId);

    boolean addExpansion(AddExpansion expansion);

    boolean removeExpansionWithName(String name);
    boolean removeExpansionWithId(int id);
    ExpansionEntry getExpansionWithName(String name);
    ExpansionEntry getExpansionWithId(int id);
    List<ExpansionEntry> getExpansionsWithGame(int gameId);

    List<ExpansionEntry> getAllExpansions();
}
