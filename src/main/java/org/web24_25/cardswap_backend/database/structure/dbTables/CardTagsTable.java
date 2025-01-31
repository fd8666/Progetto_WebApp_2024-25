package org.web24_25.cardswap_backend.database.structure.dbTables;

import java.util.List;

public interface CardTagsTable {
    static CardTagsTable getInstance() {
        return null;
    }
    boolean addTagToCard(int cardId, int tagId);
    boolean removeCardTagFromTable(int cardId, int tagId);
    boolean removeAllCardTagsFromCard(int cardId);
    boolean removeAllCardTagsFromTag(int tagId);
}
