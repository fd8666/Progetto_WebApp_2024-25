package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.TagEntry;

import java.util.List;

public interface TagsTable {
    static TagsTable getInstance() {
        return null;
    }
    boolean addTag(String name);
    boolean removeTagWithName(String name);
    boolean removeTagWithId(int id);
    TagEntry getTagWithName(String name);
    TagEntry getTagWithId(int id);
    List<TagEntry> getTagsForCard(int cardId);
}
