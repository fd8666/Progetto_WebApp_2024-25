package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.InventoryEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.InventoriesTable;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class InventoriesTablePostgres implements InventoriesTable {
    private static final HashMap<Integer, InventoryEntry> inventories = new HashMap<>();
    private static InventoriesTablePostgres instance;
    public static final Logger logger = Logger.getLogger(InventoriesTablePostgres.class.getName());

    public static InventoriesTablePostgres getInstance() {
        if (instance == null) {
            instance = new InventoriesTablePostgres();
        }
        return instance;
    }

    private InventoriesTablePostgres() {}

    @Override
    public boolean addInventory(Integer userId, Integer cardId, Integer amount) {
        return false;
    }

    @Override
    public List<InventoryEntry> getInventoriesWithUser(Integer userId) {
        return List.of();
    }

    @Override
    public List<InventoryEntry> getInventoriesWithCard(Integer cardId) {
        return List.of();
    }
}
