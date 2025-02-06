package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.InventoryEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.InventoryEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.InventoriesTable;

import java.sql.ResultSet;
import java.util.ArrayList;
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
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("INSERT INTO inventories (\"user\", card, amount) VALUES (?, ?, ?);");
                ps.setInt(1, userId);
                ps.setInt(2, cardId);
                ps.setInt(3, amount);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public List<InventoryEntry> getInventoriesWithUser(Integer userId) {
        ArrayList<InventoryEntry> inventories_list = new ArrayList<>();
        for (InventoryEntry ie : inventories.values()) {
            if (ie.user().equals(userId)) {
                inventories_list.add(ie);
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM inventories WHERE \"user\" = ?;");
                ps.setInt(1, userId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (inventories_list.contains(inventories.get(rs.getInt("id")))) {
                        inventories_list.add(inventories.get(rs.getInt("id")));
                    } else {
                        InventoryEntryPostgres iep = new InventoryEntryPostgres(
                                rs.getInt("id"),
                                rs.getInt("user"),
                                rs.getInt("card"),
                                rs.getInt("amount")
                        );
                        inventories.put(iep.id(), iep);
                        inventories_list.add(iep);
                    }
                }
                rs.close();
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return inventories_list;
    }

    @Override
    public List<InventoryEntry> getInventoriesWithCard(Integer cardId) {
        ArrayList<InventoryEntry> inventories_list = new ArrayList<>();
        for (InventoryEntry ie : inventories.values()) {
            if (ie.card().equals(cardId)) {
                inventories_list.add(ie);
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM inventories WHERE card = ?;");
                ps.setInt(1, cardId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    if (inventories_list.contains(inventories.get(rs.getInt("id")))) {
                        inventories_list.add(inventories.get(rs.getInt("id")));
                    } else {
                        InventoryEntryPostgres iep = new InventoryEntryPostgres(
                                rs.getInt("id"),
                                rs.getInt("user"),
                                rs.getInt("card"),
                                rs.getInt("amount")
                        );
                        inventories.put(iep.id(), iep);
                        inventories_list.add(iep);
                    }
                }
                rs.close();
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return inventories_list;
    }

    @Override
    public InventoryEntry getInventoryWithCardWithUser(Integer cardId, Integer UserId) {
        for (InventoryEntry ie : inventories.values()) {
            if (ie.card().equals(cardId) && ie.user().equals(UserId)) {
                return ie;
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM inventories WHERE card = ? AND \"user\" = ?;");
                ps.setInt(1, cardId);
                ps.setInt(2, UserId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    InventoryEntryPostgres iep = new InventoryEntryPostgres(
                            rs.getInt("id"),
                            rs.getInt("user"),
                            rs.getInt("card"),
                            rs.getInt("amount")
                    );
                    inventories.put(iep.id(), iep);
                    return iep;
                }
                rs.close();
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }
}
