package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.ExpansionEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.ExpansionsTable;

import java.util.HashMap;
import java.util.logging.Logger;

public class ExpansionsTablePostgres implements ExpansionsTable {
    private static final HashMap<Integer, ExpansionsTable> expansions = new HashMap<>();
    private static ExpansionsTablePostgres instance;
    public static final Logger logger = Logger.getLogger(ExpansionsTablePostgres.class.getName());

    public static ExpansionsTablePostgres getInstance() {
        if (instance == null) {
            instance = new ExpansionsTablePostgres();
        }
        return instance;
    }

    @Override
    public boolean addExpansion(String name, int gameId) {
        return false;
    }

    @Override
    public boolean removeExpansionWithName(String name) {
        return false;
    }

    @Override
    public boolean removeExpansionWithId(int id) {
        return false;
    }

    @Override
    public ExpansionEntry getExpansionWithName(String name) {
        return null;
    }

    @Override
    public ExpansionEntry getExpansionWithId(int id) {
        return null;
    }

    private ExpansionsTablePostgres() {}
}
