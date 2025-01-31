package org.web24_25.cardswap_backend.database.structure.dbEntry;

import java.util.List;

public interface ExpansionEntry {
    Integer id();
    Integer game();
    String name();
    String description();
    boolean setName(String name);
    boolean setDescription(String description);
    GameEntry getGame();
    List<CardEntry> getCards();
}
