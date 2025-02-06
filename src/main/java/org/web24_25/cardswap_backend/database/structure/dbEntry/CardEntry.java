package org.web24_25.cardswap_backend.database.structure.dbEntry;

import java.util.List;

public interface CardEntry {
    Integer id();
    Integer game();
    String name();
    Integer expansion();
    String description();
    String identifier();
    boolean setName(String name);
    boolean setExpansion(Integer expansion);
    boolean setGame(Integer game);
    boolean setDescription(String description);
    boolean setIdentifier(String identifier);
    GameEntry getGame();
    ExpansionEntry getExpansion();
    List<TagEntry> getTags();
    boolean addTag(TagEntry tag);
    boolean removeTag(TagEntry tag);

    String toJson();
}
