package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.InventoryEntry;

import java.util.List;

public interface InventoriesTable {
    boolean addInventory(Integer userId, Integer cardId, Integer amount);
    List<InventoryEntry> getInventoriesWithUser(Integer userId);
    List<InventoryEntry> getInventoriesWithCard(Integer cardId);
    InventoryEntry getInventoryWithCardWithUser(Integer cardId, Integer UserId);
}
