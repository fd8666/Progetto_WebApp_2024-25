package org.web24_25.cardswap_backend.database.structure.dbEntry;

import java.util.List;

public interface GameEntry {
    Integer id();
    String name();
    String description();
    boolean setName(String name);
    boolean setDescription(String description);
    List<ExpansionEntry> getExpansions();
    List<CardEntry> getCards();
    String toJson();
}
