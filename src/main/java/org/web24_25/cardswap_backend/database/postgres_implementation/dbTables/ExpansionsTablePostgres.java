package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
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
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("INSERT INTO expansions (game, name) VALUES (?, ?);");
                ps.setInt(1, gameId);
                ps.setString(2, name);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
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
