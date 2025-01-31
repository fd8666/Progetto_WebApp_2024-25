package org.web24_25.cardswap_backend.database.structure.dbEntry;

public interface InventoryEntry {
    Integer id();
    Integer user();
    Integer card();
    Integer amount();
    CardEntry getCard();
    UserEntry getUser();
    boolean setAmount(Integer amount);
}
